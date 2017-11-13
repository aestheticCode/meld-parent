import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {MeldFileContainerComponent} from "./meld-file-container.component";
import {InputDirective} from "./input/input.directive";
import {InputMultiplyDirective} from "./input[multiply]/input[multiple].directive";
import {MdButtonModule, MdIconModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MdButtonModule,
    MdIconModule
  ],
  declarations: [
    MeldFileContainerComponent,
    InputDirective,
    InputMultiplyDirective
  ],
  exports : [
    MeldFileContainerComponent,
    InputDirective,
    InputMultiplyDirective
  ]
})
export class MeldFileContainerModule { }
