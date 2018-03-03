import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {HttpClient} from '@angular/common/http';
import {Container} from '../../../../../lib/common/rest/Container';
import {Category} from '../categories.interfaces';
import {AppService} from '../../../../app.service';
import {AbstractGuard} from '../../../../../lib/common/AbstractGuard';

@Injectable()
export class CategoriesFormGuard extends AbstractGuard<Container<Category>> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<Category>> {
    return this.http.get<Container<Category>>(`service/social/people/categories`);
  }


}
