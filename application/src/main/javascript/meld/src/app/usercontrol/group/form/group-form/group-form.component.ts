import {Component, OnInit, ViewChild} from '@angular/core';
import {Response} from '@angular/http';
import {GroupForm} from './group-form.interfaces';
import {NgModel} from '@angular/forms';
import {RoleSelect} from '../../../role/multiselect/role-multiselect/role-multiselect.interfaces';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {AbstractForm} from 'lib/common/forms/AbstractForm';
import {Container} from 'lib/common/rest/Container';
import {Observable} from 'rxjs/Observable';
import {Items} from 'lib/common/search/search.interfaces';

@Component({
  selector: 'app-group-form',
  templateUrl: 'group-form.component.html',
  styleUrls: ['group-form.component.css']
})
export class GroupFormComponent extends AbstractForm<GroupForm> implements OnInit {

  public group: GroupForm;

  @ViewChild('name')
  public name: NgModel;

  @ViewChild('input')
  public input: any;

  private router: MeldRouterService;

  constructor(http: HttpClient,
              router: MeldRouterService) {
    super(http);
    this.router = router;
  }

  ngOnInit() {
    this.group = this.router.data.group;

    this.name
      .control
      .valueChanges
      .debounceTime(300)
      .subscribe((value: string) => {
        if (value != null) {
          const inputElement = this.input.nativeElement;
          this.http.post('service/usercontrol/group/form/validate',
            {name: value, id: this.group.id})
            .subscribe((res: Response) => {
              let isValid = res;
              if (!isValid) {
                inputElement.invalid = true;
              }
            });
        }
      });

  }

  roles: Items<RoleSelect> = (query, response) => {
    this.http.post<Container<RoleSelect>>('service/usercontrol/role/table', query)
      .subscribe((res: Container<RoleSelect>) => {
        response(res.rows, res.size);
      });
  };

  public saveRequest(): Observable<GroupForm> {
    return this.http.post<GroupForm>(`service/usercontrol/group/form`, this.group);
  }

  public updateRequest(): Observable<GroupForm> {
    return this.http.put<GroupForm>(`service/usercontrol/group/${this.group.id}/form`, this.group);
  }

  public deleteRequest(): Observable<GroupForm> {
    return this.http.delete<GroupForm>(`service/usercontrol/group/${this.group.id}/form`);
  }

  public postRequest(form: GroupForm) {
    this.router.navigate(['usercontrol', 'groups']);
  }
}
