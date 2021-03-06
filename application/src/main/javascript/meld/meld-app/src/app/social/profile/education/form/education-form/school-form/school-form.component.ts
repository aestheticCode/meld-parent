import {Component, EventEmitter, Input, Output, ViewEncapsulation} from '@angular/core';
import {Enum} from '@aestheticcode/meld-lib';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Strings} from '@aestheticcode/meld-lib';
import {FormGroup} from '@angular/forms';


@Component({
  selector: 'app-social-school-form',
  templateUrl: 'school-form.component.html',
  styleUrls: ['school-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SchoolFormComponent {

  @Input('school')
  public school: FormGroup;

  @Output('delete')
  private delete: EventEmitter<any> = new EventEmitter<any>();

  public semesters: Enum[] = [
    {
      value: 'WINTER',
      label: 'Winter'
    },
    {
      value: 'SUMMER',
      label: 'Summer'
    }
  ];

  public years: number[] = [];

  public names: Observable<any>;

  constructor(private http: HttpClient) {
    for (let i = 1950; i < 2050; i++) {
      this.years.push(i);
    }
  }

  findNames(value: string): Observable<any> {
    if (Strings.isEmpty(value)) {
      return Observable.of(null);
    }
    this.names = this.http.get<any>('service/social/education/name', {params: {name: value}});
  }

  onDelete() {
    this.delete.emit();
  }

}
