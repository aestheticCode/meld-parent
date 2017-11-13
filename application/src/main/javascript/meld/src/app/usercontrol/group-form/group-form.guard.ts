import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {GroupForm} from "./GroupForm";
import {AppService} from "../../app.service";

@Injectable()
export class GroupFormGuard implements Resolve<GroupForm> {

    constructor(private http: Http,
                private router: Router,
                private app: AppService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GroupForm> {
        return this.http.get(`service/usercontrol/group/${route.params['id']}/form`)
            .map((res: Response) => {
                return res.json() as GroupForm;
            })
            .catch((error: Response) => {
                this.app.redirectUrl = state.url;
                this.router.navigate(['usercontrol/login']);
                return Observable.of(null);
            })
    }
}
