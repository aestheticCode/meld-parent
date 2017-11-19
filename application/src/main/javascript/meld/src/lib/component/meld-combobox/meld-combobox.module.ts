import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {MeldComboBoxComponent} from "./meld-combobox.component";
import {MatButtonModule, MatCheckboxModule, MatFormFieldModule, MatIconModule, MatInputModule} from "@angular/material";
import {MeldTableModule} from "../meld-table/meld-table.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatCheckboxModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
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
