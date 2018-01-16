import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeldFilterComponent } from './meld-filter.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldFilterComponent
  ],
  exports : [
    MeldFilterComponent
  ]
})
export class MeldFilterModule { }
