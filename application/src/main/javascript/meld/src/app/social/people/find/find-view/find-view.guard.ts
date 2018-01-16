import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppService} from '../../../../app.service';
import {AbstractGuard} from '../../../../../lib/common/AbstractGuard';
import {RestExpression} from '../../../../../lib/common/search/expression.interfaces';

@Injectable()
export class FindViewGuard extends AbstractGuard<RestExpression[]> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RestExpression[]> {
    return this.http.get<RestExpression[]>(`service/social/people/find/meta`);
  }


}




