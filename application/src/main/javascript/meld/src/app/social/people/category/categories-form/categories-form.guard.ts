import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Container} from '../../../../../lib/common/rest/Container';
import {Category} from '../categories.interfaces';
import {AppService} from '../../../../app.service';

@Injectable()
export class CategoriesFormGuard implements Resolve<Container<Category>> {

  constructor(private http: HttpClient,
              private router: Router,
              private app: AppService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<Category>> {
    return this.http.post<Container<Category>>(`service/social/people/categories`, {})
      .map((res: Container<Category>) => {
        return res;
      })
      .catch((error: HttpErrorResponse) => {
        this.app.redirectUrl = state.url;
        this.router.navigate(['usercontrol/login']);
        return Observable.of(null);
      })
  }
}
