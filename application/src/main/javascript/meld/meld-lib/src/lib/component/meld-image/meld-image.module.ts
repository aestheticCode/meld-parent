import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldImageComponent} from "./meld-image.component";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldImageComponent
  ],
  exports : [
    MeldImageComponent
  ]
})
export class MeldImageModule { }
