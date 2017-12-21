import {Component, OnInit, ViewChild} from '@angular/core';
import {Http, Response} from '@angular/http';
import {NgModel} from '@angular/forms';
import {UserForm} from '../user.interfaces';
import {UserFormModel} from '../user.classes';
import {Enum} from '../../../../../lib/pipe/meld-enum/meld-enum.interfaces';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';
import {AbstractForm} from '../../../../../lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Places} from '../../places/places.interfaces';
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

  public genders: Enum[] = [{value: 'MALE', label: 'Male'}, {value: 'FEMALE', label: 'Female'}];

  private router: MeldRouterService;

  constructor(http: HttpClient,
              router: MeldRouterService) {
    super(http);
    this.router = router;
  }

  ngOnInit() {
    this.user = this.router.data.user;
  }

  public saveRequest(): Observable<UserForm> {
    return this.http.post<UserForm>( `service/social/user/current/form`, this.user)
  }

  public updateRequest(): Observable<UserForm> {
    return this.http.put<UserForm>('service/social/user/current/form', this.user)
  }

  public deleteRequest(): Observable<UserForm> {
    return this.http.delete<UserForm>('service/social/user/current/form')
  }



  public postRequest() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['user', 'view']}}]);
  }

}
