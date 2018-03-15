import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldWindowComponent} from "./meld-window.component";
import {MeldWindowBodyDirective} from "./meld-window-body/meld-window-body.directive";
import {MeldWindowFooterDirective} from "./meld-window-footer/meld-window-footer.directive";
import {MeldWindowHeaderDirective} from "./meld-window-head/meld-window-header.directive";
import {MeldScrollbarHorizontalModule} from "../meld-scrollbar-horizontal/meld-scrollbar-horizontal.module";
import {MeldScrollbarVerticalModule} from "../meld-scrollbar-vertical/meld-scrollbar-vertical.module";
import {MeldTouchWheelModule} from '../../directive/meld-touch-wheel/meld-touch-wheel.module';

@NgModule({
  imports: [
    CommonModule,
    MeldScrollbarHorizontalModule,
    MeldScrollbarVerticalModule,
    MeldTouchWheelModule
  ],
  declarations: [
    MeldWindowComponent,
    MeldWindowBodyDirective,
    MeldWindowFooterDirective,
    MeldWindowHeaderDirective
  ],
  exports: [
    MeldWindowComponent,
    MeldWindowBodyDirective,
    MeldWindowFooterDirective,
    MeldWindowHeaderDirective
  ]
})
export class MeldWindowModule { }
