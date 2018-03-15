import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatAutocompleteModule, MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule} from '@angular/material';
import {MeldGoogleMapsAutocompleteComponent} from './meld-google-maps-autocomplete.component';
import {FormsModule} from '@angular/forms';
import {MeldComboBoxModule} from '../meld-combobox/meld-combobox.module';

@NgModule({
  imports: [
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MeldComboBoxModule,
    CommonModule,
    FormsModule
  ],
  declarations: [
    MeldGoogleMapsAutocompleteComponent
  ],
  exports: [
    MeldGoogleMapsAutocompleteComponent
  ]
})

export class MeldGoogleMapsAutocompleteModule {
}
