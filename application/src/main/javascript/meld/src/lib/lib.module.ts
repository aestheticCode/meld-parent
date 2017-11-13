import {NgModule} from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MeldScrollbarVerticalModule} from "./component/meld-scrollbar-vertical/meld-scrollbar-vertical.module";
import {MeldScrollbarHorizontalModule} from "./component/meld-scrollbar-horizontal/meld-scrollbar-horizontal.module";
import {MeldTableModule} from "./component/meld-table/meld-table.module";
import {MeldWindowModule} from "./component/meld-window/meld-window.module";
import {
  MdButtonModule,
  MdCardModule,
  MdCheckboxModule,
  MdDatepickerModule,
  MdDialogModule,
  MdFormFieldModule,
  MdIconModule,
  MdInputModule,
  MdListModule,
  MdNativeDateModule,
  MdProgressBarModule,
  MdProgressSpinnerModule,
  MdSidenavModule,
  MdTabsModule,
  MdToolbarModule,
  MdSelectModule,
  MdMenuModule
} from "@angular/material";
import {MeldBinaryImageModule} from "./pipe/meld-binary-image/meld-binary-image.module";
import {MeldHtmlModule} from "./pipe/meld-html/meld-html.module";
import {MeldLinkModule} from "./pipe/meld-link/meld-link.module";
import {HttpModule} from "@angular/http";
import {MeldComboBoxModule} from "./component/meld-combobox/meld-combobox.module";
import {MeldFileContainerModule} from "./component/meld-file-container/meld-file-container.module";
import {MeldMultiSelectModule} from "./component/meld-multiselect/meld-multiselect.module";
import {MeldImageModule} from "./component/meld-image/meld-image.module";
import {MeldListModule} from "./component/meld-list/meld-list.module";
import {MeldDimensionsModule} from "./directive/meld-dimensions/meld-dimensions.module";
import {MeldInitModule} from "./directive/meld-init/meld-init.module";
import {MeldEditorModule} from "./component/meld-editor/meld-editor.module";
import {MeldDateModule} from "./component/meld-date/meld-date.module";
import {MeldDatepickerModule} from "./component/meld-datepicker/meld-datepicker.module";


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,

    MdButtonModule,
    MdCardModule,
    MdCheckboxModule,
    MdDatepickerModule,
    MdDialogModule,
    MdFormFieldModule,
    MdIconModule,
    MdInputModule,
    MdListModule,
    MdNativeDateModule,
    MdProgressBarModule,
    MdProgressSpinnerModule,
    MdSidenavModule,
    MdTabsModule,
    MdToolbarModule,
    MdSelectModule,
    MdMenuModule,

    MeldComboBoxModule,
    MeldDateModule,
    MeldDatepickerModule,
    MeldEditorModule,
    MeldFileContainerModule,
    MeldImageModule,
    MeldListModule,
    MeldMultiSelectModule,
    MeldScrollbarVerticalModule,
    MeldScrollbarHorizontalModule,
    MeldTableModule,
    MeldWindowModule,

    MeldDimensionsModule,
    MeldInitModule,

    MeldBinaryImageModule,
    MeldHtmlModule,
    MeldLinkModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    HttpModule,

    MdButtonModule,
    MdCardModule,
    MdCheckboxModule,
    MdDatepickerModule,
    MdDialogModule,
    MdFormFieldModule,
    MdIconModule,
    MdInputModule,
    MdListModule,
    MdNativeDateModule,
    MdProgressBarModule,
    MdProgressSpinnerModule,
    MdSidenavModule,
    MdTabsModule,
    MdToolbarModule,
    MdSelectModule,
    MdMenuModule,

    MeldComboBoxModule,
    MeldDateModule,
    MeldDatepickerModule,
    MeldEditorModule,
    MeldFileContainerModule,
    MeldImageModule,
    MeldListModule,
    MeldMultiSelectModule,
    MeldScrollbarVerticalModule,
    MeldScrollbarHorizontalModule,
    MeldTableModule,
    MeldWindowModule,

    MeldDimensionsModule,
    MeldInitModule,

    MeldBinaryImageModule,
    MeldHtmlModule,
    MeldLinkModule
  ]
})
export class LibModule {
}
