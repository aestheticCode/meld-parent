import {EventEmitter, Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Configuration} from "./Configuration";

@Injectable()
export class AppService {

  redirectUrl: string = "/";

  event: EventEmitter<Configuration> = new EventEmitter<Configuration>();

  configuration: Configuration;

  constructor(private http: HttpClient) {}

  load(): Promise<any> {
    return new Promise((resolve: any) => {
      this.http.get('service')
        .subscribe(data => {
          this.configuration = data as Configuration;
          this.event.emit(this.configuration);
          resolve(true);
        });
    });
  }

}
