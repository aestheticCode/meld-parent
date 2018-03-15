import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LibModule} from '@aestheticcode/meld-lib';
import {appRoutingProviders, routing} from './media.routing';
import {PhotosGridComponent} from './photo/grid/photos-grid/photos-grid.component';
import {PhotoFormComponent} from './photo/form/photo-form/photo-form.component';
import {PhotoDialogComponent} from './photo/grid/photo-dialog/photo-dialog.component';
import {PhotoViewComponent} from './photo/form/photo-view/photo-view.component';

@NgModule({
  imports: [
    LibModule,
    CommonModule,
    routing
  ],
  declarations: [
    PhotosGridComponent,
    PhotoFormComponent,
    PhotoDialogComponent,
    PhotoViewComponent
  ],
  exports : [
    PhotoDialogComponent,
    PhotoViewComponent
  ],
  entryComponents : [
    PhotoFormComponent,
    PhotoDialogComponent
  ],
  providers: [
    appRoutingProviders
  ]
})
export class MediaModule {
}
