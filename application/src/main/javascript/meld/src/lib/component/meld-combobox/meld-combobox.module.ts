import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {MeldComboBoxComponent} from "./meld-combobox.component";
import {MdButtonModule, MdCheckboxModule, MdFormFieldModule, MdIconModule, MdInputModule} from "@angular/material";
import {MeldTableModule} from "../meld-table/meld-table.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MdCheckboxModule,
    MdIconModule,
    MdButtonModule,
    MdFormFieldModule,
    MdInputModule,
    MeldTableModule
  ],
  declarations: [
    MeldComboBoxComponent
  ],
  exports : [
    MeldComboBoxComponent
  ]
})
export class MeldComboBoxModule { }
