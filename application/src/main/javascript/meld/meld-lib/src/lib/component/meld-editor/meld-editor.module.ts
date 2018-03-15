import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {MeldEditorComponent} from "./meld-editor.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [
    MeldEditorComponent
  ],
  exports : [
    MeldEditorComponent
  ]
})
export class MeldEditorModule { }
