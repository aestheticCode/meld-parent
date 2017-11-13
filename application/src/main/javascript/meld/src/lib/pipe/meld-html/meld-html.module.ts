import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldHtmlPipe} from "./meld-html.pipe";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldHtmlPipe
  ],
  exports : [
    MeldHtmlPipe
  ]
})
export class MeldHtmlModule { }
