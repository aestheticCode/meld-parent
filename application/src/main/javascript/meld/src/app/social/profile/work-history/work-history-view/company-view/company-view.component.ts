import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {Company} from '../../company.interfaces';
import {MatDialog} from '@angular/material';
import {MeldGoogleMapsMarkerComponent} from 'lib/component/meld-google-maps-marker/meld-google-maps-marker.component';

@Component({
  selector: 'app-social-company-view',
  templateUrl: 'company-view.component.html',
  styleUrls: ['company-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CompanyViewComponent {

  @Input("company")
  company : Company;

  constructor(public dialog: MatDialog) {}

  open() {
    this.dialog.open(MeldGoogleMapsMarkerComponent, {
      data: this.company.location,
      width: '300px',
      height: '300px'
    });
  }


}
