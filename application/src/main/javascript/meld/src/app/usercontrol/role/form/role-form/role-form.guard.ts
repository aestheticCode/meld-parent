import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {RoleForm} from './role-form.interfaces';
import {AppService} from '../../../../app.service';
import {HttpClient} from '@angular/common/http';
import {AbstractGuard} from '../../../../../lib/common/AbstractGuard';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class RoleFormGuard extends AbstractGuard<RoleForm> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RoleForm> {
    return this.http.get<RoleForm>(`service/usercontrol/role/${route.params['id']}/form`);
  }

}

@Injectable()
export class RoleCreateGuard extends AbstractGuard<RoleForm> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RoleForm> {
    return this.http.get<RoleForm>(`service/usercontrol/role/create/form`);
  }

}
