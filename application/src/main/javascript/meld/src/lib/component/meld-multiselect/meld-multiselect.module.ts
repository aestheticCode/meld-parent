import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from "@angular/forms";
import {MeldMultiSelectComponent} from "./meld-multiselect.component";
import {MatButtonModule, MatFormFieldModule, MatIconModule} from '@angular/material';
import {MeldComboBoxModule} from "../meld-combobox/meld-combobox.module";
import {MeldTableModule} from "../meld-table/meld-table.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MeldComboBoxModule,
    MeldTableModule
  ],
  declarations: [
    MeldMultiSelectComponent
  ],
  exports: [
    MeldMultiSelectComponent
  ]
})
export class MeldMultiSelectModule {
}
