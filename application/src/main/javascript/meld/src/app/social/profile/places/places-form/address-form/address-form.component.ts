import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgModel} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {MatAutocompleteSelectedEvent} from "@angular/material";
import {Address} from '../../address.interfaces';

@Component({
  selector: 'app-social-address-form',
  templateUrl: 'address-form.component.html',
  styleUrls: ['address-form.component.css']
})
export class AddressFormComponent implements OnInit {

  @Input("address")
  public address: Address;

  @Input("readonly")
  public readonly: boolean = true;

  @Output("deleteClick")
  private deleteClick: EventEmitter<Address> = new EventEmitter();

  @ViewChild("street")
  private street: NgModel;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {}

  onDelete() {
    this.address.location.streetNumber = undefined;
    this.address.location.country = undefined;
    this.address.location.state = undefined;
    this.address.location.street = undefined;
    this.address.location.zipCode = undefined;

    this.deleteClick.emit(this.address);
  }

}
