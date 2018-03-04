import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UserForm} from '../user.interfaces';
import {Enum} from '../../../../../lib/pipe/meld-enum/meld-enum.interfaces';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';
import {AbstractForm} from '../../../../../lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {UserDialogComponent} from './user-dialog/user-dialog.component';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-user-form',
  templateUrl: 'user-form.component.html',
  styleUrls: ['user-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserFormComponent extends AbstractForm<UserForm> implements OnInit {

  public user: UserForm;

  public genders: Enum[] = [{value: 'MALE', label: 'Male'}, {value: 'FEMALE', label: 'Female'}];

  constructor(private http: HttpClient,
              private dialog: MatDialog,
              private router: MeldRouterService) {
    super();
  }

  ngOnInit() {
    this.user = this.router.data.user;
  }

  onVisibility() {
    this.dialog.open(UserDialogComponent, {data: this.user, width: 400 + 'px'});
  }

  public saveRequest(): Observable<UserForm> {
    return this.http.post<UserForm>(`service/social/user/current/form`, this.user);
  }

  public updateRequest(): Observable<UserForm> {
    return this.http.put<UserForm>('service/social/user/current/form', this.user);
  }

  public deleteRequest(): Observable<UserForm> {
    return this.http.delete<UserForm>('service/social/user/current/form');
  }


  public postRequest() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['user', 'view']}}]);
  }

}
