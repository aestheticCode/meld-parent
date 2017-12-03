import { Injectable } from '@angular/core';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Container} from "../../../lib/common/rest/Container";
import {UserRow} from "./user-table.interfaces";
import {AppService} from "../../app.service";
import {HttpClient, HttpResponse} from "@angular/common/http";

@Injectable()
export class UserTableGuard implements Resolve<Container<UserRow>> {

  constructor(private http: HttpClient,
              private router: Router,
              private app: AppService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<UserRow>> {
    return this.http.post<Container<UserRow>>('service/usercontrol/user/table', {start: 0, limit: 0})
      .map((res: Container<UserRow>) => {
        return res;
      })
      .catch((error: Response) => {
        this.app.redirectUrl = state.url;
        this.router.navigate(['usercontrol/login']);
        return Observable.of(null);
      })
  }
}
