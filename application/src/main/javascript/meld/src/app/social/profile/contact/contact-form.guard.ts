import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Contact} from './contact-form.interfaces';
import {AppService} from 'app/app.service';
import {HttpClient, HttpResponse} from '@angular/common/http';

@Injectable()
export class ContactFormGuard implements Resolve<Contact> {

  constructor(private http: HttpClient,
              private router: Router,
              private app: AppService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Contact> {
    return this.http.get(`service/social/user/${route.parent.params['id']}/contact`)
      .map((res: Contact) => {
        return res;
      })
      .catch((error: HttpResponse<Contact>) => {
        if (error.status === 404) {
          return this.http.get(`service/social/user/${route.parent.params['id']}/contact/create`)
            .map((res: Contact) => {
              return res;
            })
            .catch((error: HttpResponse<Contact>) => {
              if (error.status === 403)
                this.app.redirectUrl = state.url;
              this.router.navigate(['usercontrol/login']);
              return Observable.of(null);
            });
        }
        if (error.status === 403)
          this.app.redirectUrl = state.url;
        this.router.navigate(['usercontrol/login']);
        return Observable.of(null);
      });
  }
}
