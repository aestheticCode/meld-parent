import {Component, Input, OnInit} from '@angular/core';
import {School} from '../../school-form.interfaces';

@Component({
  selector: 'app-social-school-view',
  templateUrl: 'school-view.component.html',
  styleUrls: ['school-view.component.css']
})
export class SchoolViewComponent implements OnInit {

  @Input('school')
  public school: School;

  constructor() { }

  ngOnInit() {
  }

}
