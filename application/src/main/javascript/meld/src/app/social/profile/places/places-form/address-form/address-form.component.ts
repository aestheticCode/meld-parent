import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {FormGroup, NgForm, NgModel} from '@angular/forms';
import {HttpClient} from "@angular/common/http";
import {MatAutocompleteSelectedEvent} from "@angular/material";
import {Address} from '../../address.interfaces';

@Component({
  selector: 'app-social-address-form',
  templateUrl: 'address-form.component.html',
  styleUrls: ['address-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class AddressFormComponent {

  @Input("address")
  public address: FormGroup;

  @Output("delete")
  private delete : EventEmitter<any> = new EventEmitter<any>();

  onDelete() {
    this.delete.emit();
  }

}
