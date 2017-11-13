import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldLinkPipe} from "./meld-link.pipe";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldLinkPipe
  ],
  exports : [
    MeldLinkPipe
  ]
})
export class MeldLinkModule { }
