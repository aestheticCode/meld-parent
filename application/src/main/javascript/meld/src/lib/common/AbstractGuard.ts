import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import {AppService} from '../../app/app.service';
import {HttpClient, HttpResponse} from '@angular/common/http';

export abstract class AbstractGuard<E> implements Resolve<E> {

  constructor(protected http: HttpClient,
              protected router: Router,
              protected app: AppService) {
  }

  abstract httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<E>;

  errorHandler(status : number, route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : Observable<E> {
    return Observable.of(null);
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<E> {
    return this.httpRequest(route, state)
      .map((res: E) => {
        return res;
      })
      .catch((error: HttpResponse<E>) => {
        if (error.status === 403) {
          this.app.redirectUrl = state.url;
          this.router.navigate(['usercontrol/login']);
          return Observable.of(null);
        } else {
          return this.errorHandler(error.status, route, state);
        }
      });
  }
}
