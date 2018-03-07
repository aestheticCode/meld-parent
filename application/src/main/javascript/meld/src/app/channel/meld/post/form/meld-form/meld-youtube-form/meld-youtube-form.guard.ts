import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AbstractGuard} from 'lib/common/AbstractGuard';
import {HttpClient} from '@angular/common/http';
import {MeldYouTubePost} from './meld-youtube-form.interfaces';
import {AppService} from '../../../../../../app.service';

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
