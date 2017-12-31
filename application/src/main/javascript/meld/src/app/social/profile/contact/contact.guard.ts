import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Contact} from './contact.interfaces';
import {AppService} from 'app/app.service';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {AbstractGuard} from '../../../../lib/common/AbstractGuard';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class ContactFormGuard extends AbstractGuard<Contact> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Contact> {
    return this.http.get<Contact>(`service/social/user/${route.parent.params['id']}/contact`);
  }

  errorHandler(status: number, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Contact> {
    if (status === 404) {
      return this.http.get(`service/social/user/${route.parent.params['id']}/contact/create`)
        .map((res: Contact) => {
          return res;
        })
        .catch((error: HttpResponse<Contact>) => {
          if (error.status === 403) {
            this.app.redirectUrl = state.url;
            this.router.navigate(['usercontrol/login']);
            return Observable.of(null);
          }
        });
    }
  }

}
