import {Component, Input, OnInit} from '@angular/core';
import * as moment from 'moment';

@Component({
  selector: 'meld-month-view',
  templateUrl: 'meld-month-view.component.html',
  styleUrls: ['meld-month-view.component.css']
})
export class MeldMonthViewComponent implements OnInit {

  @Input("date")
  public date = moment({year : 2013, month : 3, day:1});

  public weekDays : string[] = moment.weekdays();

  public calendar : number[][] = [];

  constructor() { }

  ngOnInit() {
    let beginDay = this.date.day();
    let endDay = this.date.daysInMonth();
    let day = 0;
    for (let weekIndex = 0; weekIndex < 5; weekIndex++) {
      let days = [];
      this.calendar.push(days);
      for (let daysIndex = 0; daysIndex < 7; daysIndex++) {
        if (weekIndex === 0 && daysIndex < beginDay) {
          days.push(undefined);
        } else {
          if (day < endDay) {
            days.push(++day)
          }

        }
      }
    }
  }

  public monthAndYear() : string {
    return moment.months(this.date.month()) + " " + this.date.year();
  }

  public weekDayShort(day : string) : string {
    return day.slice(0, 3);
  }

}
