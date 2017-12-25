import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {PermissionRow, RoleForm} from './role-form.interfaces';
import {Response} from '@angular/http';
import {NgModel} from '@angular/forms';
import {AbstractForm} from '../../../../../lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';
import {Container} from '../../../../../lib/common/rest/Container';
import {Items} from '../../../../../lib/common/search/search.interfaces';

@Component({
  selector: 'app-role-form',
  templateUrl: 'role-form.component.html',
  styleUrls: ['role-form.component.css']
})
export class RoleFormComponent extends AbstractForm<RoleForm> implements OnInit {

  public role: RoleForm;

  @ViewChild('name')
  private name: NgModel;

  @ViewChild('input')
  private input: ElementRef;

  private router: MeldRouterService;

  constructor(http: HttpClient,
              router: MeldRouterService) {
    super(http);
    this.router = router;
  }

  ngOnInit() {
    this.role = this.router.data.role;

    this.name
      .control
      .valueChanges
      .debounceTime(300)
      .subscribe((value: string) => {
        if (value != null) {
          const inputElement = this.input.nativeElement;
          this.http.post('service/usercontrol/role/form/validate',
            {name: value, id: this.role.id})
            .subscribe((res: Response) => {
              let isValid = res;
              if (!isValid) {
                inputElement.invalid = true;
              }
            });
        }
      });

  }

  permissions: Items<PermissionRow> = (query, response) => {
    this.http.post<Container<PermissionRow>>('service/usercontrol/permission/table', query)
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
