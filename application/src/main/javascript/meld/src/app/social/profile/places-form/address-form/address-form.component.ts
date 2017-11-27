import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Address} from "./address.interfaces";

@Component({
  selector: 'app-social-address-form',
  templateUrl: 'address-form.component.html',
  styleUrls: ['address-form.component.css']
})
export class AddressFormComponent {

  @Input("address")
  public address: Address;

  @Input("readonly")
  public readonly: boolean = true;

  @Output("deleteClick")
  private deleteClick: EventEmitter<Address> = new EventEmitter();

  onDelete() {
    this.address.city = undefined;
    this.address.country = undefined;
    this.address.state = undefined;
    this.address.street = undefined;
    this.address.zipCode = undefined;

    this.deleteClick.emit(this.address);
  }

}
