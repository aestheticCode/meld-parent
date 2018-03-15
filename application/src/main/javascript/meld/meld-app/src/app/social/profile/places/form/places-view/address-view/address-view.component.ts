import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {Address} from '../../../address.interfaces';
import {MatDialog} from '@angular/material';
import {MeldGoogleMapsMarkerComponent} from '@aestheticcode/meld-lib';

@Component({
  selector: 'app-social-address-view',
  templateUrl: 'address-view.component.html',
  styleUrls: ['address-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class AddressViewComponent  {

  @Input('address')
  public address: Address;

  constructor(public dialog : MatDialog) {}

  open() {
    this.dialog.open(MeldGoogleMapsMarkerComponent, {
      data : this.address.location,
      width : '300px',
      height : '300px'
    })
  }

}
