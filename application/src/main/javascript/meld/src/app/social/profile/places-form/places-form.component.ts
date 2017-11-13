import { Component, OnInit } from '@angular/core';
import {Strings} from "../../../../lib/common/utils/Strings";
import {Places} from "./places-form.interfaces";
import {Http, Response} from "@angular/http";
import {ActivatedRoute} from "@angular/router";
import {PlacesModel} from "./places-form.classes";
import {AddressModel} from "./address-form/address-form.classes";
import {Address} from "./address-form/address-form.interfaces";

@Component({
  selector: 'app-social-places-form',
  templateUrl: 'places-form.component.html',
  styleUrls: ['places-form.component.css']
})
export class PlacesFormComponent implements OnInit {

  public readonly : boolean = true;

  public places: Places;

  constructor(private http: Http,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { places: Places }) => {
      this.places = data.places || new PlacesModel();
    });
  }

  onCreateAddress() {
    this.places.addresses.push(new AddressModel())
  }

  onDeleteAddress(address: Address) {
    if (this.places.addresses.length > 1) {
      let indexOf = this.places.addresses.indexOf(address);
      this.places.addresses.splice(indexOf, 1);
    }
  }

  onEdit() {
    if (this.places.addresses.length === 0) {
      this.places.addresses.push(new AddressModel());
    }
    this.readonly = false;
  }

  onSave() {
    this.filterEmptyAddress();
    this.http.post("service/social/user/current/places", this.places)
      .subscribe((res: Response) => {
        this.places = res.json();
        this.readonly = true;
      })
  }

  onUpdate() {
    this.filterEmptyAddress();
    this.http.put("service/social/user/current/places", this.places)
      .subscribe((res: Response) => {
        this.places = res.json();
        this.readonly = true;
      })
  }

  onCancel() {
    this.readonly = true;
    this.filterEmptyAddress();

  }

  private filterEmptyAddress() {
    this.places.addresses
      = this.places.addresses.filter((address) => {
      return Strings.isNotEmpty(address.street)
        || Strings.isNotEmpty(address.zipCode)
        || Strings.isNotEmpty(address.city)
        || Strings.isNotEmpty(address.state)
        || Strings.isNotEmpty(address.country)
    })
  }

}
