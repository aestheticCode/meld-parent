import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LibModule} from '../../lib/lib.module';
import {PhotosGridComponent} from './photos-grid/photos-grid.component';
import {PhotoViewComponent} from './photos-grid/photo-view/photo-view.component';
import {appRoutingProviders, routing} from './media.routing';

@NgModule({
  imports: [
    LibModule,
    CommonModule,
    routing
  ],
  declarations: [
    PhotosGridComponent,
    PhotoViewComponent
  ],
  providers: [
    appRoutingProviders
  ]
})
export class MediaModule {
}
