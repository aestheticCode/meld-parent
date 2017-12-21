import {Component, OnInit} from '@angular/core';
import {WorkHistory} from '../work-history.interfaces';
import {CompanyModel} from '../company.classes';
import {Company} from '../company.interfaces';
import {Strings} from 'lib/common/utils/Strings';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {AbstractForm} from 'lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Places} from '../../places/places.interfaces';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'app-work-history-form',
  templateUrl: 'work-history-form.component.html',
  styleUrls: ['work-history-form.component.css']
})
export class WorkHistoryFormComponent extends AbstractForm<WorkHistory> implements OnInit {

  public workHistory: WorkHistory;
  private router: MeldRouterService;

  constructor(http: HttpClient,
              router: MeldRouterService) {
    super(http);
    this.router = router;
  }

  ngOnInit() {
    this.workHistory = this.router.data.workHistory;
  }

  onCreateAddress() {
    this.workHistory.companies.push(new CompanyModel());
  }

  onDeleteAddress(address: Company) {
    if (this.workHistory.companies.length > 0) {
      let indexOf = this.workHistory.companies.indexOf(address);
      this.workHistory.companies.splice(indexOf, 1);
    }
  }

  onEdit() {
    if (this.workHistory.companies.length === 0) {
      this.workHistory.companies.push(new CompanyModel());
    }
  }

  public saveRequest(): Observable<WorkHistory> {
    return this.http.post<WorkHistory>( 'service/social/user/current/work/history', this.workHistory)
  }

  public updateRequest(): Observable<WorkHistory> {
    return this.http.put<WorkHistory>('service/social/user/current/work/history', this.workHistory)
  }

  public deleteRequest(): Observable<WorkHistory> {
    return this.http.delete<WorkHistory>('service/social/user/current/work/history')
  }

  public preRequest() {
    this.filterEmpty();
  }

  public postRequest() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['work', 'history', 'view']}}]);
  }

  private filterEmpty() {
    this.workHistory.companies
      = this.workHistory.companies.filter((company) => {
      return Strings.isNotEmpty(company.name)
        || Strings.isNotEmpty(company.description)
        || Strings.isNotEmpty(company.start)
        || Strings.isNotEmpty(company.end);
    });
  }

}
