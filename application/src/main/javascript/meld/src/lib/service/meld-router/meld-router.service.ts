import {Injectable} from '@angular/core';
import {ActivatedRoute, NavigationExtras, Router} from '@angular/router';

@Injectable()
export class MeldRouterService {

  constructor(private router: Router,
              private route: ActivatedRoute) {
  }

  get param() : any {
    let object = {};
    this.extractedParams(this.route, object);
    return object;
  }

  get queryParam() : any {
    let object = {};
    this.extractedQueryParams(this.route, object);
    return object;
  }

  get data() : any {
    let object = {};
    this.extractedData(this.route, object);
    return object;
  }

  private extractedData(route: ActivatedRoute, dataObject : any) {
    route.data.forEach((data: any) => {
      Object.assign(dataObject, data);
    });
    if (route.firstChild instanceof ActivatedRoute) {
      this.extractedData(route.firstChild, dataObject);
    }
  }


  private extractedQueryParams(route: ActivatedRoute, paramObject: any) {
    route.queryParams.forEach((param) => {
      Object.assign(paramObject, param);
    });
    if (route.firstChild instanceof ActivatedRoute) {
      this.extractedQueryParams(route.firstChild, paramObject);
    }
  }

  private extractedParams(route: ActivatedRoute, paramObject: any) {
    route.params.forEach((param) => {
      Object.assign(paramObject, param);
    });
    if (route.firstChild instanceof ActivatedRoute) {
      this.extractedParams(route.firstChild, paramObject);
    }
  }

  navigate(commands: any[], extras?: NavigationExtras): Promise<boolean> {
    return this.router.navigate(commands, extras);
  }

}
