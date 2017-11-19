import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import * as moment from 'moment';

@Component({
  selector: 'meld-month-view',
  templateUrl: 'meld-month-view.component.html',
  styleUrls: ['meld-month-view.component.css']
})
export class MeldMonthViewComponent implements OnInit {

  public months : string[][] = [];

  @Output("monthChange")
  private monthChange : EventEmitter<number> = new EventEmitter();

  constructor() { }

  ngOnInit() {

    this.months = [];

    let months = moment.months();
    let index = 0;

    for (let i = 0; i < 3; i++) {

      const td : string[] = [];

      for (let j = 0; j < 4; j++) {

        td.push(months[index++])


      }
      this.months.push(td);
    }

  }

  public monthShort(month : string) : string {
    return month.slice(0, 3);
  }

  onClick(month : number) {
    this.monthChange.emit(month);
  }

}
