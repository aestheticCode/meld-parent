import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, ErrorHandler, NgModule} from '@angular/core';

import {AppComponent} from 'app/app.component';
import {LibModule} from "@aestheticcode/meld-lib";
import {appRoutingProviders, routing} from "./app.routing";
import {AppHome} from "app/app.home";
import {AppService} from "app/app.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AppErrorHandler} from './app.errorhandler';

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
    },
    {
      provide: ErrorHandler,
      useClass: AppErrorHandler
    }

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
