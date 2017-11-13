import {EventEmitter, Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Configuration} from "./Configuration";

@Injectable()
export class AppService {

  redirectUrl: string = "/";

  event: EventEmitter<Configuration> = new EventEmitter<Configuration>();

  configuration: Configuration;

  constructor(private http: Http) {}

  load(): Promise<any> {
    return new Promise((resolve: any) => {
      this.http.get('service')
        .map(res => res.json() as Configuration)
        .subscribe(configuration => {
          this.configuration = configuration;
          this.event.emit(this.configuration);
          resolve(true);
        });
    });
  }

}
