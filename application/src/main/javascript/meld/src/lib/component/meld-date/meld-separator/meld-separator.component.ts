import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'meld-separator',
  templateUrl: 'meld-separator.component.html',
  styleUrls: ['meld-separator.component.css']
})
export class MeldSeparatorComponent implements OnInit {

  public value : string = "-";

  constructor() { }

  ngOnInit() {
  }

}
