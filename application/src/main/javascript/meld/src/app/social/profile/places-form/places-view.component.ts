import {Component, OnInit} from '@angular/core';
import {AddressModel} from "./address-form/address.classes";
import {PlacesModel} from "./places.classes";
import {Places} from "./places.interfaces";
import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";

@Component({
  selector: 'app-social-places-view',
  templateUrl: 'places-view.component.html',
  styleUrls: ['places-view.component.css']
})
export class PlacesViewComponent implements OnInit {

  public places: Places;

  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { places: Places }) => {
      this.places = data.places || new PlacesModel();
    });
  }

  onEdit() {
    this.router.navigate(['social', 'profile', {outlets: {profile: ['places', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile']);
  }


}
