import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldYearViewComponent} from "./meld-year-view/meld-year-view.component";
import {MeldDatePickerComponent} from "./meld-datepicker.component";
import {MeldDayViewComponent} from "./meld-day-view/meld-day-view.component";
import {MatButtonModule, MatIconModule} from "@angular/material";
import {MeldWindowModule} from "../meld-window/meld-window.module";
import {MeldMonthViewComponent} from "./meld-month-view/meld-month-view.component";

@NgModule({
  imports: [
    CommonModule,
    MeldWindowModule,
    MatButtonModule,
    MatIconModule
  ],
  declarations: [
    MeldDatePickerComponent,
    MeldDayViewComponent,
    MeldMonthViewComponent,
    MeldYearViewComponent
  ],
  exports : [
    MeldDatePickerComponent
  ],
  entryComponents : [
    MeldDatePickerComponent
  ]
})
export class MeldDatepickerModule { }
