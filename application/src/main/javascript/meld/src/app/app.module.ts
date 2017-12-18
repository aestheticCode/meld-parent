import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppComponent} from 'app/app.component';
import {LibModule} from "lib/lib.module";
import {appRoutingProviders, routing} from "./app.routing";
import {AppHome} from "app/app.home";
import {AppService} from "app/app.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

export function initConfiguration(configService: AppService): Function {
  return () => configService.load();
}

@NgModule({
  declarations: [
    AppComponent,
    AppHome
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    LibModule.forRoot(),
    routing
  ],
  providers: [
    appRoutingProviders,
    AppService,
    {provide: 'Window', useValue: window},
    {
      provide: APP_INITIALIZER,
      useFactory: initConfiguration,
      deps: [AppService],
      multi: true
    }

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
