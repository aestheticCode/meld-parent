import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldYearViewComponent} from "./meld-year-view/meld-year-view.component";
import {MeldDatePickerComponent} from "./meld-datepicker.component";
import {MeldMonthViewComponent} from "./meld-month-view/meld-month-view.component";
import {MdButtonModule} from "@angular/material";
import {MeldWindowModule} from "../meld-window/meld-window.module";

@NgModule({
  imports: [
    CommonModule,
    MeldWindowModule,
    MdButtonModule
  ],
  declarations: [
    MeldDatePickerComponent,
    MeldMonthViewComponent,
    MeldYearViewComponent
  ],
  exports : [
    MeldDatePickerComponent
  ]
})
export class MeldDatepickerModule { }
