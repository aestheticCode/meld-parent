import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {AppService} from "../../../app.service";
import {Places} from "./places.interfaces";

@Injectable()
export class PlacesFormGuard implements Resolve<Places> {

  constructor(private http: Http,
              private router: Router,
              private app: AppService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Places> {
    return this.http.get(`service/social/user/current/places`)
      .map((res: Response) => {
        return res.json() as Places;
      })
      .catch((error: Response) => {
        this.app.redirectUrl = state.url;
        this.router.navigate(['usercontrol/login']);
        return Observable.of(null);
      })
  }
}
