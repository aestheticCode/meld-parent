import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {RoleRow} from './role-table.intefaces';
import {AppService} from '../../../../app.service';
import {HttpClient} from '@angular/common/http';
import {AbstractGuard} from '../../../../../lib/common/AbstractGuard';
import {Observable} from 'rxjs/Observable';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';

@Injectable()
export class RoleTableGuard extends AbstractGuard<RoleRow> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RoleRow> {
    return this.http.post<RoleRow>('service/usercontrol/role/table', QueryBuilder.query());
  }

}
