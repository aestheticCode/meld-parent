import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatAutocompleteModule, MatButtonModule, MatFormFieldModule, MatIconModule, MatIconRegistry,
  MatInputModule
} from '@angular/material';
import {MeldGoogleMapsAutocompleteComponent} from './meld-google-maps-autocomplete.component';
import {FormsModule} from '@angular/forms';
import {MeldComboBoxModule} from '../meld-combobox/meld-combobox.module';
import {MeldGoogleMapsDetailsModule} from '../meld-google-maps-details/meld-google-maps-details.module';

@NgModule({
  imports: [
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MeldComboBoxModule,
    MeldGoogleMapsDetailsModule,
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
