import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PhotosGridComponent} from './photo/grid/photos-grid/photos-grid.component';

const appRoutes: Routes = [
  {path: 'photos', component: PhotosGridComponent},
];
export const appRoutingProviders: any[] = [
];
export const routing: ModuleWithProviders = RouterModule.forChild(appRoutes);
