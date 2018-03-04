import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {WorkHistory} from '../work-history.interfaces';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {AbstractForm} from 'lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {Link} from '../../../../../lib/common/rest/Link';
import {WorkHistoryDialogComponent} from './work-history-dialog/work-history-dialog.component';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-work-history-form',
  templateUrl: 'work-history-form.component.html',
  styleUrls: ['work-history-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class WorkHistoryFormComponent extends AbstractForm<WorkHistory> implements OnInit {

  public workHistory: FormGroup;

  public links: Link[];

  constructor(private http: HttpClient,
              private dialog : MatDialog,
              private builder: FormBuilder,
              private router: MeldRouterService) {
    super();
  }

  ngOnInit() {
    let workHistory: WorkHistory = this.router.data.workHistory;

    this.links = workHistory.links;

    this.workHistory = this.builder.group({
      id: this.builder.control(workHistory.id),
      categories : this.builder.control(workHistory.categories),
      companies: this.builder.array(workHistory.companies.map((company) => this.builder.group(company)))
    });
  }

  get companies() {
    return this.workHistory.get('companies') as FormArray;
  }

  onCreateAddress() {
    this.companies.push(this.builder.group({
      id: this.builder.control(''),
      name: this.builder.control(''),
      location: this.builder.control(null),
      title: this.builder.control(''),
      description: this.builder.control(''),
      start: this.builder.control(null),
      end: this.builder.control(null),
      tillNow: this.builder.control(false),
    }));
  }

  onDeleteAddress(index: number) {
    this.companies.removeAt(index);
  }

  onVisibility() {
    this.dialog.open(WorkHistoryDialogComponent, {data: this.workHistory, width: 400 + 'px'});
  }

  public preRequest(): boolean {
    return this.validateAllFields(this.workHistory);
  }

  public saveRequest(): Observable<WorkHistory> {
    return this.http.post<WorkHistory>('service/social/user/current/work/history', this.workHistory.getRawValue());
  }

  public updateRequest(): Observable<WorkHistory> {
    return this.http.put<WorkHistory>('service/social/user/current/work/history', this.workHistory.getRawValue());
  }

  public deleteRequest(): Observable<WorkHistory> {
    return this.http.delete<WorkHistory>('service/social/user/current/work/history');
  }

  public postRequest() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['work', 'history', 'view']}}]);
  }

}
