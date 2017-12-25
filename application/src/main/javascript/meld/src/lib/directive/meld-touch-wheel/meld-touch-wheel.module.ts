import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldTouchWheelDirective} from './meld-touch-wheel.directive';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldTouchWheelDirective
  ],
  exports : [
    MeldTouchWheelDirective
  ]
})
export class MeldTouchWheelModule {}
