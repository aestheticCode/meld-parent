import {Component, Input, OnInit} from '@angular/core';
import {Address} from '../../address.interfaces';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-social-address-view',
  templateUrl: 'address-view.component.html',
  styleUrls: ['address-view.component.css']
})
export class AddressViewComponent implements OnInit {

  @Input('address')
  public address: Address;

  constructor() { }

  ngOnInit() {
  }

}
