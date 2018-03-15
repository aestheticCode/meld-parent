import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldGridComponent} from './meld-grid.component';
import {MeldWindowModule} from '../meld-window/meld-window.module';
import {MatCheckboxModule} from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatCheckboxModule,
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
