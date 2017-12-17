import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Category} from '../../categories.interfaces';
import {AppService} from '../../../../../app.service';

@Injectable()
export class CategoryFormGuard implements Resolve<Category> {

  constructor(private http: HttpClient,
              private router: Router,
              private app: AppService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Category> {
    return this.http.get<Category>(`service/social/people/category/${route.params['id']}`)
      .map((res: Category) => {
        return res;
      })
      .catch((error: HttpErrorResponse) => {
        this.app.redirectUrl = state.url;
        this.router.navigate(['usercontrol/login']);
        return Observable.of(null);
      });
  }
}
