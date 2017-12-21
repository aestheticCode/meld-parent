import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {GroupRow} from './group-table.interfaces';
import {AppService} from '../../../../app.service';
import {HttpClient} from '@angular/common/http';
import {AbstractGuard} from '../../../../../lib/common/AbstractGuard';
import {Observable} from 'rxjs/Observable';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';

@Injectable()
export class GroupTableGuard extends AbstractGuard<GroupRow> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GroupRow> {
    return this.http.post<GroupRow>('service/usercontrol/group/table', QueryBuilder.query());
  }
}
