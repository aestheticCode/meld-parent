import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeldDialogComponent } from './meld-dialog.component';
import {MatDialogModule} from '@angular/material';

@NgModule({
  imports: [
    MatDialogModule,
    CommonModule
  ],
  declarations: [
    MeldDialogComponent
  ],
  exports : [
    MeldDialogComponent
  ]
})
export class MeldDialogModule { }
