import {Component, OnInit, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'meld-separator',
  templateUrl: 'meld-separator.component.html',
  styleUrls: ['meld-separator.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldSeparatorComponent implements OnInit {

  public value : string = "-";

  constructor() { }

  ngOnInit() {
  }

}
