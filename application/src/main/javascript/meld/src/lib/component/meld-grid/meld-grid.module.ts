import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldGridComponent} from './meld-grid.component';
import {MeldWindowModule} from '../meld-window/meld-window.module';

@NgModule({
  imports: [
    CommonModule,
    MeldWindowModule
  ],
  declarations: [
    MeldGridComponent
  ],
  exports : [
    MeldGridComponent
  ]
})
export class MeldGridModule { }
