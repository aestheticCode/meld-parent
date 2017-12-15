import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Profile} from './profile.interfaces';
import {HttpClient} from '@angular/common/http';
import {AppService} from '../../app.service';

@Injectable()
export class ProfileGuard implements Resolve<Profile> {

    constructor(private http: HttpClient,
                private router: Router,
                private app: AppService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Profile> {
        return this.http.get<Profile>(`service/social/user/${route.params['id']}/profile/background`)
            .map((res) => {
              return res;
            })
            .catch((error: Response) => {
                this.app.redirectUrl = state.url;
                this.router.navigate(['usercontrol/login']);
                return Observable.of(null);
            })
    }
}
