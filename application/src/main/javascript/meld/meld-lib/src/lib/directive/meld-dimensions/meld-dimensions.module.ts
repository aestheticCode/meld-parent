import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MeldDimensionsDirective} from "./meld-dimensions.directive";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldDimensionsDirective
  ],
  exports: [
    MeldDimensionsDirective
  ]
})
export class MeldDimensionsModule {
}
