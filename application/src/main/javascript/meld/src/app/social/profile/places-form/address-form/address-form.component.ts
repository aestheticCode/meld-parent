import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Address} from "./address.interfaces";
import {NgModel} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {MatAutocompleteSelectedEvent} from "@angular/material";

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

  public streets = [];


  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this
      .street
      .control
      .valueChanges
      .debounceTime(300)
      .subscribe((value: string) => {
        this.http.post<any>("service/social/place", {value: value})
          .subscribe((res) => {
            this.streets = res.rows;
          })
      })
  }

  process(value : string) : string {
    let parts = value.split(",");
    return parts[0];
  }

  fillForm(value : MatAutocompleteSelectedEvent) {
    let parts = value.option.viewValue.split(",");

    this.address.state = parts[1].trim();
    this.address.country = parts[2].trim();
  }

  onDelete() {
    this.address.city = undefined;
    this.address.country = undefined;
    this.address.state = undefined;
    this.address.street = undefined;
    this.address.zipCode = undefined;

    this.deleteClick.emit(this.address);
  }

}
