import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatAutocompleteModule, MatButtonModule, MatFormFieldModule, MatIconModule, MatIconRegistry,
  MatInputModule
} from '@angular/material';
import {MeldGoogleMapsAutocompleteComponent} from './meld-google-maps-autocomplete.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  imports: [
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    CommonModule,
    FormsModule
  ],
  declarations: [
    MeldGoogleMapsAutocompleteComponent
  ],
  exports : [
    MeldGoogleMapsAutocompleteComponent
  ]
})

export class MeldGoogleMapsAutocompleteModule { }
