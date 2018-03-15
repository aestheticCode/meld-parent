import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AbstractGuard} from '@aestheticcode/meld-lib';
import {HttpClient} from '@angular/common/http';
import {MeldTextPost} from './meld-text-form.interfaces';
import {AppService} from '../../../../../../app.service';

@Injectable()
export class MeldTextFormGuard extends AbstractGuard<MeldTextPost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldTextPost> {
    return this.http.get<MeldTextPost>(`service/channel/meld/${route.params['id']}`);
  }

}

@Injectable()
export class MeldTextCreateGuard extends AbstractGuard<MeldTextPost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldTextPost> {
    return this.http.get<MeldTextPost>(`service/channel/meld/create/text`);
  }

}
