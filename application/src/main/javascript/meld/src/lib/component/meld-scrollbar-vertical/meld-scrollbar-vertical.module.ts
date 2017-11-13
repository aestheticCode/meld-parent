import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldScrollbarVerticalComponent} from "./meld-scrollbar-vertical.component";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldScrollbarVerticalComponent
  ],
  exports : [
    MeldScrollbarVerticalComponent
  ]
})
export class MeldScrollbarVerticalModule { }
