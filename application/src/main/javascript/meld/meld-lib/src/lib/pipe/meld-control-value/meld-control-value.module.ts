import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeldControlValuePipe } from './meld-control-value.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldControlValuePipe
  ],
  exports : [
    MeldControlValuePipe
  ]
})
export class MeldControlValueModule { }
