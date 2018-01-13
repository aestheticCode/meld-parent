import {Component, EventEmitter, Inject, OnInit, Optional, ViewEncapsulation} from '@angular/core';
import * as moment from "moment";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Moment} from "moment";

@Component({
  selector: 'meld-datepicker',
  templateUrl: 'meld-datepicker.component.html',
  styleUrls: ['meld-datepicker.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldDatePickerComponent implements OnInit {

  public day : number = moment(new Date()).date();

  public year : number = moment(new Date()).year();

  public month : number = moment(new Date()).month();

  public dayView : boolean = true;

  public monthView : boolean = false;

  public yearView : boolean = false;

  public date = moment({year: this.year, month: this.month, day: this.day});

  private dateChange : EventEmitter<Date> = new EventEmitter();

  constructor(@Optional() private dialogRef: MatDialogRef<MeldDatePickerComponent>,
              @Inject(MAT_DIALOG_DATA) @Optional() public data: any) {
    if (data) {
      this.date = moment(data.date);
      this.day = this.date.day();
      this.month = this.date.month();
      this.year = this.date.year();
    }
  }

  previous() {
    if (this.dayView) {
      if (this.month > 0) {
        this.month --;
      } else {
        this.year--;
        this.month = 11;
      }
    } else {
        this.year -= 24
    }


    this.date = moment({year: this.year, month: this.month, day: 1});
  };

  next() {
    if (this.dayView) {
      if (this.month < 11) {
        this.month ++;
      } else {
        this.year++;
        this.month = 0;
      }
    } else {
      this.year += 24
    }

    this.date = moment({year: this.year, month: this.month, day: 1});
  }

  public monthAndYear() : string {
    return moment.months(this.date.month()) + " " + this.date.year();
  }

  onYearChange(year : number) {
    this.year = year;
    this.dayView = false;
    this.monthView = true;
    this.yearView = false;
    this.date = moment({year: this.year, month: this.month, day: this.day});
  }

  onMonthChange(month : string) {
    this.month = moment.months().indexOf(month);
    this.dayView = true;
    this.yearView = false;
    this.monthView =false;
    this.date = moment({year: this.year, month: this.month, day: this.day});
  }

  onDayChange(day : number) {
    this.day = day;
    this.date = moment({year: this.year, month: this.month, day: this.day});
    this.dateChange.emit(this.date.toDate());
    if (this.dialogRef) {
      this.dialogRef.close(this.date.toDate())
    }
  }

  ngOnInit() {

  }


}
