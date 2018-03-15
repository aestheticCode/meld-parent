import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldEnumPipe} from './meld-enum.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldEnumPipe
  ],
  exports : [
    MeldEnumPipe
  ]
})
export class MeldEnumModule { }
