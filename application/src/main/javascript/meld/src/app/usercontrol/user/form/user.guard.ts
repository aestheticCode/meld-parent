import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserForm} from "./user.interfaces";
import {AppService} from '../../../app.service';
import {AbstractGuard} from '../../../../lib/common/AbstractGuard';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class UserEditGuard extends AbstractGuard<UserForm> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserForm> {
    return this.http.get<UserForm>(`service/usercontrol/user/${route.params['id']}/form`);
  }

}

@Injectable()
export class UserCreateGuard extends AbstractGuard<UserForm> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserForm> {
    return this.http.get<UserForm>(`service/usercontrol/user/create/form`)
  }
}
