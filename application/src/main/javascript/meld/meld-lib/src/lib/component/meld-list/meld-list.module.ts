import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldInitModule} from "../../directive/meld-init/meld-init.module";
import {MeldListComponent} from "./meld-list.component";

@NgModule({
  imports: [
    CommonModule,
    MeldInitModule
  ],
  declarations: [
    MeldListComponent
  ],
  exports : [
    MeldListComponent
  ]
})
export class MeldListModule { }
