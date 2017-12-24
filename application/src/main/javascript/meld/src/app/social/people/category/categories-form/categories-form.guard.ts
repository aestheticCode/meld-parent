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
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';

@Injectable()
export class CategoriesFormGuard extends AbstractGuard<Container<Category>> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<Category>> {
    const query = QueryBuilder.query();
    query.index = 0;
    query.limit = 75;
    query.expression = QueryBuilder.path("user.id", QueryBuilder.equal(this.app.configuration.user.id));
    return this.http.post<Container<Category>>(`service/social/people/categories`, query);
  }


}
