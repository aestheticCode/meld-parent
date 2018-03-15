import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldFormGroupComponent} from "./meld-form-group.component";
import {FormsModule} from "@angular/forms";
import {MatButtonModule, MatIconModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatButtonModule
  ],
  declarations: [
    MeldFormGroupComponent
  ],
  exports : [
    MeldFormGroupComponent
  ]
})
export class MeldFormGroupModule { }
