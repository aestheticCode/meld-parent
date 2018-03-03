import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {GroupRow} from './group-table.interfaces';
import {AppService} from '../../../../app.service';
import {HttpClient} from '@angular/common/http';
import {AbstractGuard} from '../../../../../lib/common/AbstractGuard';
import {Observable} from 'rxjs/Observable';
import {Container} from '../../../../../lib/common/rest/Container';

@Injectable()
export class GroupTableGuard extends AbstractGuard<Container<GroupRow>> {

  constructor(http: HttpClient, router: Router, app: AppService) {
    super(http, router, app);
  }

  httpRequest(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container<GroupRow>> {
    return this.http.get<Container<GroupRow>>('service/usercontrol/group/table', {params: {index: '0', limit: '0'}});
  }
}
