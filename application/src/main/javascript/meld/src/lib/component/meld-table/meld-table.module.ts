import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {MeldTableComponent} from "./meld-table.component";
import {MeldColDirective} from "./meld-col/meld-col.directive";
import {MeldColgroupDirective} from "./meld-colgroup/meld-colgroup.directive";
import {MeldFilterDirective} from "./meld-filter/meld-filter.directive";
import {MeldTableFilterDialogComponent} from "./meld-filter-dialog/meld-table-filter-dialog.component";
import {MeldTableMenuDialogComponent} from "./meld-menu-dialog/meld-table-menu-dialog.component";
import {MeldTbodyDirective} from "./meld-tbody/meld-tbody.directive";
import {MeldTdDirective} from "./meld-td/meld-td.directive";
import {MeldTfooterDirective} from "./meld-tfooter/meld-tfooter.directive";
import {MeldTheadDirective} from "./meld-thead/meld-thead.directive";
import {MeldTrDirective} from "./meld-tr/meld-tr.directive";
import {MdButtonModule, MdCheckboxModule, MdDialogModule, MdIconModule} from "@angular/material";
import {MeldWindowModule} from "../meld-window/meld-window.module";
import {MeldTableSorterPipe} from "./meld-table-sorter.pipe";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MdDialogModule,
    MdIconModule,
    MdButtonModule,
    MdCheckboxModule,
    MeldWindowModule
  ],
  declarations: [
    MeldTableComponent,
    MeldColDirective,
    MeldColgroupDirective,
    MeldFilterDirective,
    MeldTableFilterDialogComponent,
    MeldTableMenuDialogComponent,
    MeldTbodyDirective,
    MeldTdDirective,
    MeldTfooterDirective,
    MeldTheadDirective,
    MeldTrDirective,
    MeldTableSorterPipe
  ],
  exports : [
    MeldTableComponent,
    MeldColDirective,
    MeldColgroupDirective,
    MeldFilterDirective,
    MeldTableFilterDialogComponent,
    MeldTableMenuDialogComponent,
    MeldTbodyDirective,
    MeldTdDirective,
    MeldTfooterDirective,
    MeldTheadDirective,
    MeldTrDirective,
    MeldTableSorterPipe
  ],
  entryComponents : [
    MeldTableFilterDialogComponent,
    MeldTableMenuDialogComponent
  ]
})
export class MeldTableModule { }
