import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {School} from '../../school-form.interfaces';


@Component({
  selector: 'app-social-school-form',
  templateUrl: 'school-form.component.html',
  styleUrls: ['school-form.component.css']
})
export class SchoolFormComponent {

  @Input("school")
  public school : School;

  @Input("readonly")
  public readonly : boolean = true;

  public semesters : string[] = ['WINTER', 'SUMMER'];

  public years : number[] = [];

  constructor() {
    for (var i = 1950; i < 2050; i++) {
      this.years.push(i);
    }
  }

  @Output("deleteClick")
  private deleteClick : EventEmitter<School> = new EventEmitter();

  onDelete() {
    this.school.name = undefined;
    this.school.course = undefined;
    this.school.start = undefined;
    this.school.end = undefined;
    this.deleteClick.emit(this.school);
  }

}
