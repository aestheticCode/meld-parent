import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'meld-month',
  templateUrl: 'meld-month.component.html',
  styleUrls: ['meld-month.component.css']
})
export class MeldMonthComponent implements OnInit {

  public value : number = 0;

  constructor() { }

  ngOnInit() {
  }

}
