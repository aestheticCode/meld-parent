import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AppService} from "../../app.service";

@Injectable()
export class MeldFormGuard implements Resolve<any> {

  constructor(private http: Http,
              private router: Router,
              private app: AppService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    return this.http.get('service/channel/meld/' + route.params['id'])
      .map((res: Response) => {
        return res.json();
      })
      .catch((error: Response) => {
        this.app.redirectUrl = state.url;
        this.router.navigate(['usercontrol/login']);
        return Observable.of(false);
      })
  }
}
