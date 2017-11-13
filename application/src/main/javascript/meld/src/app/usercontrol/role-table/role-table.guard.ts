import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Container} from "../../../lib/common/rest/Container";
import {RoleRow} from "./RoleRow";
import {AppService} from "../../app.service";
import {QueryBuilder} from "../../../lib/common/query/QueryBuilder";

@Injectable()
export class RoleTableGuard implements Resolve<Container<RoleRow>> {

    constructor(private http: Http,
                private router: Router,
                private app: AppService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<RoleRow>> {
        return this.http.post('service/usercontrol/role/table', QueryBuilder.query())
            .map((res: Response) => {
                return res.json() as Container<RoleRow>;
            })
            .catch((error: Response) => {
                this.app.redirectUrl = state.url;
                this.router.navigate(['usercontrol/login']);
                return Observable.of(null);
            })
    }
}
