import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AppService} from '../../../app.service';
import {Places} from './places.interfaces';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {AbstractGuard} from '@aestheticcode/meld-lib';

@Injectable()
export class PlacesFormGuard extends AbstractGuard<Places> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Places> {
    return this.http.get<Places>(`service/social/user/${route.parent.params['id']}/places`);
  }

  errorHandler(status: number, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Places> {
    if (status === 404) {
      return this.http.get<Places>(`service/social/user/${route.parent.params['id']}/places/create`)
        .map((res: Places) => {
          return res;
        })
        .catch((error: HttpResponse<Places>) => {
          if (error.status === 403) {
            this.app.redirectUrl = state.url;
            this.router.navigate(['usercontrol/login']);
            return Observable.of(null);
          }
        });
    }
  }

}

