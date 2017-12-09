import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldSelectViewComponent} from './meld-select-view.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldSelectViewComponent
  ],
  exports : [
    MeldSelectViewComponent
  ]
})
export class MeldSelectViewModule { }
