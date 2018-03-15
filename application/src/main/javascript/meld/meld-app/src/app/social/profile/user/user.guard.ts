import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserForm} from './user.interfaces';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {AppService} from '../../../app.service';
import {Observable} from 'rxjs/Observable';
import {AbstractGuard} from '@aestheticcode/meld-lib';

@Injectable()
export class UserFormGuard extends AbstractGuard<UserForm> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserForm> {
    return this.http.get<UserForm>(`service/social/user/${route.parent.params['id']}/form`);
  }

  errorHandler(status: number, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserForm> {
    if (status === 404) {
      return this.http.get<UserForm>(`service/social/user/${route.parent.params['id']}/form/create`)
        .map((res: UserForm) => {
          return res;
        })
        .catch((error: HttpResponse<UserForm>) => {
          if (error.status === 403) {
            this.app.redirectUrl = state.url;
            this.router.navigate(['usercontrol/login']);
            return Observable.of(null);
          }
        });
    }
  }

}
