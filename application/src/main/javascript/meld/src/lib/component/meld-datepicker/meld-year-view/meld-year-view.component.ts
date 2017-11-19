import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import * as moment from 'moment';

@Component({
  selector: 'meld-year-view',
  templateUrl: 'meld-year-view.component.html',
  styleUrls: ['meld-year-view.component.css']
})
export class MeldYearViewComponent implements OnInit, OnChanges {


  @Input("date")
  public date = moment({year : 2013, month : 3, day:1});

  public years : number[][] = [];

  @Output()
  private yearChange : EventEmitter<number> = new EventEmitter();

  constructor() { }

  ngOnInit() {
    this.years = [];
    let years : number = this.date.year() - 12;
    for (let i = 1; i <= 4; i++) {
      let td : number[] = [];
      for (let j = 1; j <= 6; j++) {
        td.push(years++);
      }
      this.years.push(td);
    }

  }

  onClick(year : number) {
   this.yearChange.emit(year);
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.ngOnInit();
  }


}
