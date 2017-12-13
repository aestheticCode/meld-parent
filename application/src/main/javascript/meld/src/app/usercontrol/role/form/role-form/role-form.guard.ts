import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {RoleForm} from "./RoleForm";
import {AppService} from '../../../../app.service';

@Injectable()
export class RoleFormGuard implements Resolve<RoleForm> {

    constructor(private http: Http,
                private router: Router,
                private app: AppService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RoleForm> {
        return this.http.get(`service/usercontrol/role/${route.params['id']}/form`)
            .map((res: Response) => {
                return res.json() as RoleForm;
            })
            .catch((error: Response) => {
                this.app.redirectUrl = state.url;
                this.router.navigate(['usercontrol/login']);
                return Observable.of(null);
            })
    }
}
