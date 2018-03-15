import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {GroupForm} from './group-form.interfaces';
import {AppService} from '../../../../app.service';
import {HttpClient} from '@angular/common/http';
import {AbstractGuard} from '@aestheticcode/meld-lib';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class GroupFormGuard extends AbstractGuard<GroupForm> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GroupForm> {
    return this.http.get<GroupForm>(`service/usercontrol/group/${route.params['id']}/form`);
  }

}

@Injectable()
export class GroupCreateGuard extends AbstractGuard<GroupForm> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GroupForm> {
    return this.http.get<GroupForm>(`service/usercontrol/group/create/form`);
  }

}
