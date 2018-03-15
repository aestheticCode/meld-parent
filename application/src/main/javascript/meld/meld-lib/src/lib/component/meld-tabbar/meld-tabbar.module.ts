import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MeldTabBarComponent} from "./meld-tabbar.component";
import {MeldTabComponent} from "./meld-tab/meld-tab.component";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MeldTabBarComponent,
    MeldTabComponent
  ],
  exports : [
    MeldTabBarComponent,
    MeldTabComponent
  ]
})
export class MeldTabBarModule { }
