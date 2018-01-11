import {Component, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, NgModel} from "@angular/forms";
import {Http, Response} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {UserForm} from '../user.interfaces';
import {UserFormModel} from '../user.classes';
import {Enum} from '../../../../../lib/pipe/meld-enum/meld-enum.interfaces';
import {AbstractForm} from '../../../../../lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'app-user-form',
  templateUrl: 'user-form.component.html',
  styleUrls: ['user-form.component.css']
})
export class UserFormComponent extends AbstractForm<UserForm> implements OnInit {

  public user: UserForm;

  @ViewChild('email')
  private email: NgModel;

  public genders : Enum[] = [{value : 'MALE', label : 'Male'}, {value : 'FEMALE', label : 'Female'}];

  constructor(private http: HttpClient,
              private router : MeldRouterService) {
    super();
  }

  ngOnInit() {
    this.user = this.router.data.user;
  }

  public saveRequest(): Observable<UserForm> {
    return this.http.post<UserForm>(`service/usercontrol/user/form`, this.user)
  }

  public updateRequest(): Observable<UserForm> {
    return this.http.put<UserForm>(`service/usercontrol/user/${this.user.id}/form`, this.user)

  }

  public deleteRequest(): Observable<UserForm> {
    return this.http.delete<UserForm>(`service/usercontrol/user/${this.user.id}/form`)
  }

  public postRequest(form: UserForm) {
    this.router.navigate(['usercontrol/users']);
  }

}
