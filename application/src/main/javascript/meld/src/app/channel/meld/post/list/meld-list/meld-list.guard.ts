import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {MeldLinkPost} from '../../form/meld-form/meld-link-form/meld-link-form.interfaces';
import {HttpClient} from '@angular/common/http';
import {Container} from '../../../../../../lib/common/rest/Container';
import {AbstractGuard} from '../../../../../../lib/common/AbstractGuard';
import {AppService} from '../../../../../app.service';

@Injectable()
export class MeldListGuard extends AbstractGuard<Container<MeldLinkPost>> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<MeldLinkPost>> {
    return this.http.get<Container<MeldLinkPost>>('service/channel/meld/posts', {params: {index: '0', limit: '0'}});
  }


}
