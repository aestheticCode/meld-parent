import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Container} from "../../../lib/common/rest/Container";
import {UserRow} from "./UserRow";
import {AppService} from "../../app.service";

@Injectable()
export class UserTableGuard implements Resolve<Container<UserRow>> {

  constructor(private http: Http,
              private router: Router,
              private app: AppService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<UserRow>> {
    return this.http.post('service/usercontrol/user/table', {start: 0, limit: 0})
      .map((res: Response) => {
        return res.json() as Container<UserRow>;
      })
      .catch((error: Response) => {
        this.app.redirectUrl = state.url;
        this.router.navigate(['usercontrol/login']);
        return Observable.of(null);
      })
  }
}
