import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {HttpClient} from '@angular/common/http';
import {Category} from '../../../categories.interfaces';
import {AppService} from '../../../../../../app.service';
import {AbstractGuard} from '@aestheticcode/meld-lib';

@Injectable()
export class CategoryFormGuard extends AbstractGuard<Category> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Category> {
    return this.http.get<Category>(`service/social/people/category/${route.params['id']}`);
  }


}

@Injectable()
export class CategoryCreateGuard extends AbstractGuard<Category> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Category> {
    return this.http.get<Category>(`service/social/people/category/create`);
  }


}
