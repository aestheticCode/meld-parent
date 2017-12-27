import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeldGoogleMapsMarkerComponent } from './meld-google-maps-marker.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldGoogleMapsMarkerComponent
  ],
  exports : [
    MeldGoogleMapsMarkerComponent
  ],
  entryComponents: [
    MeldGoogleMapsMarkerComponent
  ]
})
export class MeldGoogleMapsMarkerModule { }
