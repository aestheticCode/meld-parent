import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserForm} from "./user.interfaces";
import {AppService} from '../../../app.service';

@Injectable()
export class UserEditGuard implements Resolve<UserForm> {

    constructor(private http: Http,
                private router: Router,
                private app: AppService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserForm> {
        return this.http.get(`service/usercontrol/user/${route.params['id']}/form`)
            .map((res: Response) => {
              return res.json() as UserForm;
            })
            .catch((error: Response) => {
                this.app.redirectUrl = state.url;
                this.router.navigate(['usercontrol/login']);
                return Observable.of(null);
            })
    }
}

@Injectable()
export class UserCreateGuard implements Resolve<UserForm> {

  constructor(private http: Http,
              private router: Router,
              private app: AppService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserForm> {
    return this.http.get(`service/usercontrol/user/create/form`)
      .map((res: Response) => {
        return res.json() as UserForm;
      })
      .catch((error: Response) => {
        this.app.redirectUrl = state.url;
        this.router.navigate(['usercontrol/login']);
        return Observable.of(null);
      })
  }
}
