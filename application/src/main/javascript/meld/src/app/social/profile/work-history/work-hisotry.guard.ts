import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AppService} from '../../../app.service';
import {WorkHistory} from './work-history.interfaces';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {AbstractGuard} from '../../../../lib/common/AbstractGuard';

@Injectable()
export class WorkHistoryFormGuard extends AbstractGuard<WorkHistory> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<WorkHistory> {
    return this.http.get<WorkHistory>(`service/social/user/${route.parent.params['id']}/work/history`);
  }

  errorHandler(status: number, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<WorkHistory> {
    if (status === 404) {
      return this.http.get<WorkHistory>(`service/social/user/${route.parent.params['id']}/work/history/create`)
        .map((res: WorkHistory) => {
          return res;
        })
        .catch((error: HttpResponse<WorkHistory>) => {
          if (error.status === 403) {
            this.app.redirectUrl = state.url;
            this.router.navigate(['usercontrol/login']);
            return Observable.of(null);
          }
        });
    }
  }

}
