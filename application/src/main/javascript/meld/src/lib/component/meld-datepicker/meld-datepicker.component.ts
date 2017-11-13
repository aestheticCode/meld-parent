import { Component, OnInit } from '@angular/core';
import * as moment from "moment";
import {LoadWindow} from "../meld-window/LoadWindow";

@Component({
  selector: 'meld-datepicker',
  templateUrl: 'meld-datepicker.component.html',
  styleUrls: ['meld-datepicker.component.css']
})
export class MeldDatePickerComponent implements OnInit {

  public calendar = [];

  constructor() { }

  ngOnInit() {

    for (let i = 0; i < 5; i++) {

      this.calendar.push(moment({year : 2013, month : i, day : 1}))

    }

  }

  public onWindowScroll(event : LoadWindow) {

    this.calendar = [];

    let loadIndex = event.loadIndex;
    let endIndex = event.loadIndex + event.loadLimit * 5;

    //console.log("startindex : " + loadIndex + " endindex : " + endIndex);

    for (let i = loadIndex; i < endIndex; i++) {

      this.calendar.push(moment({year : 2013, month : i, day : 1}))

    }

    //event.callback();

  }

}
