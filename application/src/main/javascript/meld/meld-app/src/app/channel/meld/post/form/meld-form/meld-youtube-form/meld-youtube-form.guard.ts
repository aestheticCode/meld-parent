import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AbstractGuard} from '@aestheticcode/meld-lib';
import {MeldYouTubePost} from './meld-youtube-form.interfaces';
import {AppService} from '../../../../../../app.service';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class MeldYoutubeFormGuard extends AbstractGuard<MeldYouTubePost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldYouTubePost> {
    return this.http.get<MeldYouTubePost>(`service/channel/meld/${route.params['id']}`);
  }

}

@Injectable()
export class MeldYoutubeCreateGuard extends AbstractGuard<MeldYouTubePost> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MeldYouTubePost> {
    return this.http.get<MeldYouTubePost>(`service/channel/meld/create/youtube`);
  }

}
