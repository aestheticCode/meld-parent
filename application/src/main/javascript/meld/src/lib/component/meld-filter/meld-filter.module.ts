import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeldFilterComponent } from './meld-filter.component';
import {MeldLikeFilterComponent} from './meld-like-filter/meld-like-filter.component';
import {MatCheckboxModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {MeldDebounceModule} from '../../directive/meld-debounce/meld-debounce.module';
import {MeldEqualFilterComponent} from './meld-equal-filter/meld-equal-filter.component';
import {MeldComboBoxModule} from '../meld-combobox/meld-combobox.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MeldDebounceModule,
    MeldComboBoxModule
  ],
  declarations: [
    MeldFilterComponent,
    MeldLikeFilterComponent,
    MeldEqualFilterComponent
  ],
  exports : [
    MeldFilterComponent
  ]
})
export class MeldFilterModule { }
