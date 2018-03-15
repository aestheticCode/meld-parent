import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserRow} from './user-table.interfaces';
import {Container} from '@aestheticcode/meld-lib';
import {AbstractGuard} from '@aestheticcode/meld-lib';
import {HttpClient} from '@angular/common/http';
import {AppService} from '../../../../app.service';

@Injectable()
export class UserTableGuard extends AbstractGuard<Container<UserRow>> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<UserRow>> {
    return this.http.get<Container<UserRow>>('service/usercontrol/user/table', {params: {index: '0', limit: '0'}});
  }

}
