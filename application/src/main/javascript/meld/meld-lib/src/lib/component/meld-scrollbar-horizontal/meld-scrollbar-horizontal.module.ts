import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldScrollbarHorizontalComponent} from "./meld-scrollbar-horizontal.component";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldScrollbarHorizontalComponent
  ],
  exports : [
    MeldScrollbarHorizontalComponent
  ]
})
export class MeldScrollbarHorizontalModule { }
