import {Component, OnInit, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-people',
  templateUrl: 'people.component.html',
  styleUrls: ['people.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class PeopleComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
