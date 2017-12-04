import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Address} from "./address.interfaces";

@Component({
  selector: 'app-social-address-map',
  templateUrl: 'address-map.component.html',
  styleUrls: ['address-map.component.css']
})
export class AddressMapComponent implements OnInit {

  @Input()
  address : Address;

  @ViewChild("map")
  map : ElementRef;

  constructor() { }

  ngOnInit() {
    let geocoder = new google.maps.Geocoder();
    let element = this.map.nativeElement;

    geocoder.geocode( {'address' : this.address.street + "," + this.address.state}, (results, status) => {
      if (status == google.maps.GeocoderStatus.OK) {
        const maps = new google.maps.Map(element, {
          center: results[0].geometry.location,
          zoom: 14
        });

        this.setMarker(maps);
      }
    });


  }

  setMarker(maps) {
    const request = {
      location: maps.getCenter(),
      radius: 500,
      query: this.address.street + "," + this.address.state
    };

    const service = new google.maps.places.PlacesService(maps);
    service.textSearch(request, callback);

    function callback(results, status) {
      if (status == google.maps.places.PlacesServiceStatus.OK) {
        const marker = new google.maps.Marker({
          map: maps,
          position: results[0].geometry.location,
          title : "Home"
        });
      }
    }
  }

}
