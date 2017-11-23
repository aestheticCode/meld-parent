import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import * as moment from 'moment';
import {Moment} from "moment";

@Component({
  selector: 'meld-day-view',
  templateUrl: 'meld-day-view.component.html',
  styleUrls: ['meld-day-view.component.css']
})
export class MeldDayViewComponent implements OnInit, OnChanges {

  public day : number;

  @Input("date")
  public date : Moment;

  @Output("dayChange")
  private dayChange : EventEmitter<number> = new EventEmitter();

  public weekDays : string[] = moment.weekdays();

  public calendar : number[][] = [];

  constructor() { }

  ngOnInit() {
    this.calendar = [];
    const date = this.date.clone();
    date.date(1);

    let beginDay = date.day();
    let endDay = date.daysInMonth();
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

  ngOnChanges(changes: SimpleChanges): void {
    this.ngOnInit();
    if (this.date) {
      this.day =  this.date.date();
    }

  }

  public weekDayShort(day : string) : string {
    return day.slice(0, 3);
  }

  public onClick(day : number) {
    this.day = day;
    this.dayChange.emit(day);
  }

  public isActive(day : number) {
    if (day) {
      return this.day === day;
    }
    return false;
  }

}
