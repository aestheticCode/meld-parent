import {Component, Input, ViewEncapsulation} from '@angular/core';
import {School} from '../../school-form.interfaces';
import {MatDialog} from '@angular/material';
import {MeldGoogleMapsMarkerComponent} from '../../../../../../lib/component/meld-google-maps-marker/meld-google-maps-marker.component';
import {Enum} from '../../../../../../lib/pipe/meld-enum/meld-enum.interfaces';

@Component({
  selector: 'app-social-school-view',
  templateUrl: 'school-view.component.html',
  styleUrls: ['school-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class SchoolViewComponent {

  @Input('school')
  public school: School;

  public semesters: Enum[] = [
    {
      value: 'SUMMER',
      label: 'Summer'
    },
    {
      value: 'WINTER',
      label: 'Winter'
    }
  ];

  constructor(public dialog: MatDialog) {
  }

  open() {
    this.dialog.open(MeldGoogleMapsMarkerComponent, {
      data: this.school.location,
      width: '300px',
      height: '300px'
    });
  }

}
