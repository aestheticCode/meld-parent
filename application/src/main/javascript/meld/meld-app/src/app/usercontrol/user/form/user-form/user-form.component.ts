import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NgModel} from '@angular/forms';
import {UserForm} from '../user.interfaces';
import {Enum} from '@aestheticcode/meld-lib';
import {AbstractForm} from '@aestheticcode/meld-lib';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from '@aestheticcode/meld-lib';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'app-user-form',
  templateUrl: 'user-form.component.html',
  styleUrls: ['user-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserFormComponent extends AbstractForm<UserForm> implements OnInit {

  public user: UserForm;

  @ViewChild('email')
  private email: NgModel;

  public genders: Enum[] = [{value: 'MALE', label: 'Male'}, {value: 'FEMALE', label: 'Female'}];

  constructor(private http: HttpClient,
              private router: MeldRouterService) {
    super();
  }

  ngOnInit() {
    this.user = this.router.data.user;
  }

  public saveRequest(): Observable<UserForm> {
    return this.http.post<UserForm>(`service/usercontrol/user/form`, this.user);
  }

  public updateRequest(): Observable<UserForm> {
    return this.http.put<UserForm>(`service/usercontrol/user/${this.user.id}/form`, this.user);

  }

  public deleteRequest(): Observable<UserForm> {
    return this.http.delete<UserForm>(`service/usercontrol/user/${this.user.id}/form`);
  }

  public postRequest(form: UserForm) {
    this.router.navigate(['usercontrol/users']);
  }

}
