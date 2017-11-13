import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldInitDirective} from "./meld-init.directive";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldInitDirective
  ],
  exports : [
    MeldInitDirective
  ]
})
export class MeldInitModule { }
