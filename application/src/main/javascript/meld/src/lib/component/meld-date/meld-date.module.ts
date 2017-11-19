import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldDateComponent} from "./meld-date.component";
import {MatFormFieldModule} from "@angular/material";
import {FormsModule} from "@angular/forms";
import {MeldDigitComponent} from "./meld-digit/meld-digit.component";
import {MeldMonthComponent} from "./meld-month/meld-month.component";
import {MeldSeparatorComponent} from "./meld-separator/meld-separator.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule
  ],
  declarations: [
    MeldDateComponent,
    MeldDigitComponent,
    MeldMonthComponent,
    MeldSeparatorComponent
  ],
  exports: [
    MeldDateComponent
  ],
  entryComponents : [
    MeldDigitComponent,
    MeldMonthComponent,
    MeldSeparatorComponent
  ]
})
export class MeldDateModule { }
