import {Component, ElementRef, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {PermissionRow, RoleForm} from './role-form.interfaces';
import {Response} from '@angular/http';
import {NgModel} from '@angular/forms';
import {AbstractForm} from '@aestheticcode/meld-lib';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {MeldRouterService} from '@aestheticcode/meld-lib';
import {Container} from '@aestheticcode/meld-lib';
import {Items} from '@aestheticcode/meld-lib';

@Component({
  selector: 'app-role-form',
  templateUrl: 'role-form.component.html',
  styleUrls: ['role-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class RoleFormComponent extends AbstractForm<RoleForm> implements OnInit {

  public role: RoleForm;

  @ViewChild('name')
  private name: NgModel;

  constructor(private http: HttpClient,
              private router: MeldRouterService) {
    super();
  }

  ngOnInit() {
    this.role = this.router.data.role;
  }

  permissions: Items<PermissionRow> = (query, response) => {

    const params = {
      index : query.index.toString(),
      limit : query.limit.toString(),
      sort : query.sort
    };

    this.http.get<Container<PermissionRow>>('service/usercontrol/permission/table', {params : params})
      .subscribe((res: Container<PermissionRow>) => {
        const json = res;
        response(json.rows, json.size);
      });
  };

  saveRequest(): Observable<RoleForm> {
    return this.http.post<RoleForm>('service/usercontrol/role/form', this.role);

  }

  updateRequest(): Observable<RoleForm> {
    return this.http.put<RoleForm>(`service/usercontrol/role/${this.role.id}/form`, this.role);

  }

  deleteRequest(): Observable<RoleForm> {
    return this.http.delete<RoleForm>(`service/usercontrol/role/${this.role.id}/form`);
  }

  public postRequest(form: RoleForm) {
    this.router.navigate(['usercontrol', 'roles']);
  }
}
