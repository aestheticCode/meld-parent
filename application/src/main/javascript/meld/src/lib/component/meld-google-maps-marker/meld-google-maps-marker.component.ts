import {Component, ElementRef, Inject, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'meld-google-maps-marker',
  templateUrl: 'meld-google-maps-marker.component.html',
  styleUrls: ['meld-google-maps-marker.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldGoogleMapsMarkerComponent implements OnInit {

  @ViewChild('map')
  private map: ElementRef<HTMLDivElement>;

  constructor(@Inject(MAT_DIALOG_DATA) private place: any) {}

  ngOnInit() {

    const place = {lat: this.place.lat, lng: this.place.lng};

    const map = new google.maps.Map(this.map.nativeElement, {
      zoom: 14,
      center: place
    });

    new google.maps.Marker({
      position: place,
      map: map
    });

  }

}
