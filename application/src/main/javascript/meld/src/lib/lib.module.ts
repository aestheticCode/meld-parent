import {NgModule} from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MeldScrollbarVerticalModule} from "./component/meld-scrollbar-vertical/meld-scrollbar-vertical.module";
import {MeldScrollbarHorizontalModule} from "./component/meld-scrollbar-horizontal/meld-scrollbar-horizontal.module";
import {MeldTableModule} from "./component/meld-table/meld-table.module";
import {MeldWindowModule} from "./component/meld-window/meld-window.module";
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatNativeDateModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatSidenavModule,
  MatTabsModule,
  MatToolbarModule,
  MatSelectModule,
  MatMenuModule, MatExpansionModule, MatAutocompleteModule
} from "@angular/material";
import {MeldBinaryImageModule} from "./pipe/meld-binary-image/meld-binary-image.module";
import {MeldHtmlModule} from "./pipe/meld-html/meld-html.module";
import {MeldLinkModule} from "./pipe/meld-link/meld-link.module";
import {HttpModule} from "@angular/http";
import {HttpClientModule} from '@angular/common/http';
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
import {MeldFormGroupModule} from "./component/meld-form-group/meld-form-group.module";
import {MeldTabBarModule} from "./component/meld-tabbar/meld-tabbar.module";
import {MeldSelectViewModule} from './component/meld-select-view/meld-select-view.module';
import {MeldPlaceholderModule} from './pipe/meld-placeholder/meld-placeholder.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    HttpModule,

    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatNativeDateModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatTabsModule,
    MatToolbarModule,
    MatSelectModule,
    MatMenuModule,
    MatExpansionModule,
    MatAutocompleteModule,

    MeldComboBoxModule,
    MeldDateModule,
    MeldDatepickerModule,
    MeldEditorModule,
    MeldFileContainerModule,
    MeldFormGroupModule,
    MeldImageModule,
    MeldListModule,
    MeldMultiSelectModule,
    MeldScrollbarVerticalModule,
    MeldScrollbarHorizontalModule,
    MeldSelectViewModule,
    MeldMultiSelectModule,
    MeldTableModule,
    MeldTableModule,
    MeldWindowModule,

    MeldDimensionsModule,
    MeldInitModule,

    MeldBinaryImageModule,
    MeldHtmlModule,
    MeldLinkModule,
    MeldPlaceholderModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    HttpModule,

    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatNativeDateModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatTabsModule,
    MatToolbarModule,
    MatSelectModule,
    MatMenuModule,
    MatExpansionModule,
    MatAutocompleteModule,

    MeldComboBoxModule,
    MeldDateModule,
    MeldDatepickerModule,
    MeldEditorModule,
    MeldFileContainerModule,
    MeldFormGroupModule,
    MeldImageModule,
    MeldListModule,
    MeldMultiSelectModule,
    MeldScrollbarVerticalModule,
    MeldScrollbarHorizontalModule,
    MeldSelectViewModule,
    MeldMultiSelectModule,
    MeldTabBarModule,
    MeldTableModule,
    MeldWindowModule,

    MeldDimensionsModule,
    MeldInitModule,

    MeldBinaryImageModule,
    MeldHtmlModule,
    MeldLinkModule,
    MeldPlaceholderModule
  ]
})
export class LibModule {
}
