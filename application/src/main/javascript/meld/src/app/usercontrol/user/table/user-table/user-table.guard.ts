import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserRow} from './user-table.interfaces';
import {Container} from 'lib/common/rest/Container';
import {AbstractGuard} from '../../../../../lib/common/AbstractGuard';
import {HttpClient} from '@angular/common/http';
import {AppService} from '../../../../app.service';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';

@Injectable()
export class UserTableGuard extends AbstractGuard<Container<UserRow>> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<UserRow>> {
    return this.http.post<Container<UserRow>>('service/usercontrol/user/table', QueryBuilder.query());
  }

}
