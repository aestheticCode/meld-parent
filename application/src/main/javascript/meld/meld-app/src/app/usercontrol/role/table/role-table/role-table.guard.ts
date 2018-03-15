import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {RoleRow} from './role-table.intefaces';
import {AppService} from '../../../../app.service';
import {HttpClient} from '@angular/common/http';
import {AbstractGuard} from '@aestheticcode/meld-lib';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class RoleTableGuard extends AbstractGuard<RoleRow> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RoleRow> {
    return this.http.get<RoleRow>('service/usercontrol/role/table', {params: {index: '0', limit: '0'}});
  }

}
