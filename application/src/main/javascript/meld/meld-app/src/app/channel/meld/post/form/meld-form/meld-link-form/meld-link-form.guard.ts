import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AbstractGuard} from '@aestheticcode/meld-lib';
import {HttpClient} from '@angular/common/http';
import {MeldLinkPost} from './meld-link-form.interfaces';
import {AppService} from '../../../../../../app.service';

@Injectable()
export class MeldLinkFormGuard extends AbstractGuard<MeldLinkPost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldLinkPost> {
    return this.http.get<MeldLinkPost>(`service/channel/meld/${route.params['id']}`);
  }

}

@Injectable()
export class MeldLinkCreateGuard extends AbstractGuard<MeldLinkPost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldLinkPost> {
    return this.http.get<MeldLinkPost>(`service/channel/meld/create/link`);
  }

}
