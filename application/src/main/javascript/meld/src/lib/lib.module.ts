import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MeldScrollbarVerticalModule} from './component/meld-scrollbar-vertical/meld-scrollbar-vertical.module';
import {MeldScrollbarHorizontalModule} from './component/meld-scrollbar-horizontal/meld-scrollbar-horizontal.module';
import {MeldTableModule} from './component/meld-table/meld-table.module';
import {MeldWindowModule} from './component/meld-window/meld-window.module';
import {
  MatAutocompleteModule, MatButtonModule, MatButtonToggleModule, MatCardModule, MatCheckboxModule, MatDatepickerModule,
  MatDialogModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule,
  MatNativeDateModule, MatProgressBarModule, MatProgressSpinnerModule, MatSelectModule, MatSidenavModule, MatTabsModule, MatToolbarModule
} from '@angular/material';
import {MeldBinaryImageModule} from './pipe/meld-binary-image/meld-binary-image.module';
import {MeldHtmlModule} from './pipe/meld-html/meld-html.module';
import {MeldLinkModule} from './pipe/meld-link/meld-link.module';
import {HttpModule} from '@angular/http';
import {HttpClientModule} from '@angular/common/http';
import {MeldComboBoxModule} from './component/meld-combobox/meld-combobox.module';
import {MeldFileContainerModule} from './component/meld-file-container/meld-file-container.module';
import {MeldMultiSelectModule} from './component/meld-multiselect/meld-multiselect.module';
import {MeldImageModule} from './component/meld-image/meld-image.module';
import {MeldListModule} from './component/meld-list/meld-list.module';
import {MeldDimensionsModule} from './directive/meld-dimensions/meld-dimensions.module';
import {MeldInitModule} from './directive/meld-init/meld-init.module';
import {MeldEditorModule} from './component/meld-editor/meld-editor.module';
import {MeldDateModule} from './component/meld-date/meld-date.module';
import {MeldDatepickerModule} from './component/meld-datepicker/meld-datepicker.module';
import {MeldFormGroupModule} from './component/meld-form-group/meld-form-group.module';
import {MeldTabBarModule} from './component/meld-tabbar/meld-tabbar.module';
import {MeldSelectViewModule} from './component/meld-select-view/meld-select-view.module';
import {MeldPlaceholderModule} from './pipe/meld-placeholder/meld-placeholder.module';
import {MeldGridModule} from './component/meld-grid/meld-grid.module';
import {MeldDialogModule} from './component/meld-dialog/meld-dialog.module';
import {MeldGoogleMapsAutocompleteModule} from './component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.module';
import {MeldEnumModule} from './pipe/meld-enum/meld-enum.module';
import {MeldRouterService} from './service/meld-router/meld-router.service';
import {MeldTouchWheelModule} from './directive/meld-touch-wheel/meld-touch-wheel.module';
import {MeldGoogleMapsMarkerModule} from './component/meld-google-maps-marker/meld-google-maps-marker.module';
import {MeldDebounceModule} from './directive/meld-debounce/meld-debounce.module';
import {MeldControlValueModule} from './pipe/meld-control-value/meld-control-value.module';
import {MeldFilterModule} from './component/meld-filter/meld-filter.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    HttpModule,

    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatSidenavModule,
    MatTabsModule,
    MatToolbarModule,

    MeldComboBoxModule,
    MeldDateModule,
    MeldDatepickerModule,
    MeldDialogModule,
    MeldEditorModule,
    MeldFileContainerModule,
    MeldFilterModule,
    MeldFormGroupModule,
    MeldGoogleMapsAutocompleteModule,
    MeldGoogleMapsMarkerModule,
    MeldImageModule,
    MeldGridModule,
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
    MeldTouchWheelModule,
    MeldDebounceModule,

    MeldBinaryImageModule,
    MeldControlValueModule,
    MeldEnumModule,
    MeldHtmlModule,
    MeldLinkModule,
    MeldPlaceholderModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
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
    MatButtonToggleModule,
    MatSelectModule,
    MatMenuModule,
    MatExpansionModule,
    MatAutocompleteModule,

    MeldComboBoxModule,
    MeldDateModule,
    MeldDatepickerModule,
    MeldDialogModule,
    MeldEditorModule,
    MeldFileContainerModule,
    MeldFilterModule,
    MeldFormGroupModule,
    MeldGoogleMapsAutocompleteModule,
    MeldGoogleMapsMarkerModule,
    MeldGridModule,
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
    MeldTouchWheelModule,
    MeldDebounceModule,

    MeldBinaryImageModule,
    MeldControlValueModule,
    MeldEnumModule,
    MeldHtmlModule,
    MeldLinkModule,
    MeldPlaceholderModule
  ]
})
export class LibModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: LibModule,
      providers: [MeldRouterService],
    };
  }

}
