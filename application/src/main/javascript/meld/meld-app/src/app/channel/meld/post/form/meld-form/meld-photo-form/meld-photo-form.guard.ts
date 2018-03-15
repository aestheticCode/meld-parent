import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AbstractGuard} from '@aestheticcode/meld-lib';
import {HttpClient} from '@angular/common/http';
import {MeldPhotoPost} from './meld-photo-form.interfaces';
import {AppService} from '../../../../../../app.service';

@Injectable()
export class MeldPhotoFormGuard extends AbstractGuard<MeldPhotoPost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldPhotoPost> {
    return this.http.get<MeldPhotoPost>(`service/channel/meld/${route.params['id']}`);
  }

}

@Injectable()
export class MeldPhotoCreateGuard extends AbstractGuard<MeldPhotoPost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldPhotoPost> {
    return this.http.get<MeldPhotoPost>(`service/channel/meld/create/photo`);
  }

}
