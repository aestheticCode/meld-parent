import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeldGoogleMapsDetailsComponent } from './meld-google-maps-details.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldGoogleMapsDetailsComponent
  ],
  exports : [
    MeldGoogleMapsDetailsComponent
  ]
})
export class MeldGoogleMapsDetailsModule { }
