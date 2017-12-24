import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AbstractGuard} from 'lib/common/AbstractGuard';
import {HttpClient} from '@angular/common/http';
import {MeldImagePost} from './meld-image-form.interfaces';
import {AppService} from '../../../../../app.service';

@Injectable()
export class MeldImageFormGuard extends AbstractGuard<MeldImagePost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldImagePost> {
    return this.http.get<MeldImagePost>(`service/channel/meld/${route.params['id']}`);
  }

}

@Injectable()
export class MeldImageCreateGuard extends AbstractGuard<MeldImagePost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldImagePost> {
    return this.http.get<MeldImagePost>(`service/channel/meld/create/image`);
  }

}
