import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserForm} from "./user-form.interfaces";
import {AppService} from "../../../app.service";

@Injectable()
export class UserFormGuard implements Resolve<UserForm> {

    constructor(private http: Http,
                private router: Router,
                private app: AppService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserForm> {
        return this.http.get(`service/social/user/current/form`)
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
