import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldPlaceholderPipe} from './meld-placeholder.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldPlaceholderPipe
  ],
  exports : [
    MeldPlaceholderPipe
  ]
})
export class MeldPlaceholderModule { }
