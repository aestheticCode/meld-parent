import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldBinaryImagePipe} from "./meld-binary-image.pipe";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldBinaryImagePipe
  ],
  exports: [
    MeldBinaryImagePipe
  ]
})
export class MeldBinaryImageModule { }
