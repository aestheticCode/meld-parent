import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeldDebounceDirective } from './meld-debounce.directive';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldDebounceDirective
  ],
  exports : [
    MeldDebounceDirective
  ]
})
export class MeldDebounceModule { }
