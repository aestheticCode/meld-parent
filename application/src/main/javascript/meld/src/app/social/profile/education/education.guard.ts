import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AppService} from '../../../app.service';
import {Education} from './education.interfaces';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Contact} from '../contact/contact-form.interfaces';
import {AbstractGuard} from '../../../../lib/common/AbstractGuard';

@Injectable()
export class EducationFormGuard extends AbstractGuard<Education> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Education> {
    return this.http.get<Education>(`service/social/user/${route.parent.params['id']}/education`);
  }

  errorHandler(status: number, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Education> {
    if (status === 404) {
      return this.http.get(`service/social/user/${route.parent.params['id']}/education/create`)
        .map((res: Contact) => {
          return res;
        })
        .catch((error: HttpResponse<Education>) => {
          if (error.status === 403) {
            this.app.redirectUrl = state.url;
            this.router.navigate(['usercontrol/login']);
            return Observable.of(null);
          }
        });
    }
  }

}
