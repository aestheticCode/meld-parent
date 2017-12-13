import { Component, OnInit } from '@angular/core';
import {Http, Response} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {Places} from '../places.interfaces';
import {PlacesModel} from '../places.classes';
import {AddressModel} from '../address.classes';
import {Address} from '../address.interfaces';
import {Strings} from '../../../../../lib/common/utils/Strings';

@Component({
  selector: 'app-social-places-form',
  templateUrl: 'places-form.component.html',
  styleUrls: ['places-form.component.css']
})
export class PlacesFormComponent implements OnInit {

  public places: Places;

  constructor(private http: Http,
              private route: ActivatedRoute,
              private router : Router) {
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
    if (this.places.addresses.length > 0) {
      let indexOf = this.places.addresses.indexOf(address);
      this.places.addresses.splice(indexOf, 1);
    }
  }

  onEdit() {
    if (this.places.addresses.length === 0) {
      this.places.addresses.push(new AddressModel());
    }
  }

  onSave() {
    this.filterEmptyAddress();
    this.http.post("service/social/user/current/places", this.places)
      .subscribe((res: Response) => {
        this.places = res.json();
        this.router.navigate(['social', 'profile', {outlets: {profile: ['places', 'view']}}]);
      })
  }

  onUpdate() {
    this.filterEmptyAddress();
    this.http.put("service/social/user/current/places", this.places)
      .subscribe((res: Response) => {
        this.places = res.json();
        this.router.navigate(['social', 'profile', {outlets: {profile: ['places', 'view']}}]);
      })
  }

  onCancel() {
    this.filterEmptyAddress();
    this.router.navigate(['social', 'profile', {outlets: {profile: ['places', 'view']}}]);
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
