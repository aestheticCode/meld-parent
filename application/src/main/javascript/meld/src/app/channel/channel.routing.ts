import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MeldListComponent} from './meld/list/meld-list/meld-list.component';
import {MeldListGuard} from './meld/list/meld-list/meld-list.guard';
import {MeldFormComponent} from './meld/form/meld-form/meld-form.component';
import {MeldImageCreateGuard, MeldImageFormGuard} from './meld/form/meld-form/meld-image-form/meld-image-form.guard';
import {MeldImageFormComponent} from './meld/form/meld-form/meld-image-form/meld-image-form.component';
import {MeldPhotoFormComponent} from './meld/form/meld-form/meld-photo-form/meld-photo-form.component';
import {MeldTextFormComponent} from './meld/form/meld-form/meld-text-form/meld-text-form.component';
import {MeldYoutubeFormComponent} from './meld/form/meld-form/meld-youtube-form/meld-youtube-form.component';
import {MeldPhotoCreateGuard, MeldPhotoFormGuard} from './meld/form/meld-form/meld-photo-form/meld-photo-form.guard';
import {MeldTextCreateGuard, MeldTextFormGuard} from './meld/form/meld-form/meld-text-form/meld-text-form.guard';
import {MeldYoutubeCreateGuard, MeldYoutubeFormGuard} from './meld/form/meld-form/meld-youtube-form/meld-youtube-form.guard';
import {MeldLinkCreateGuard, MeldLinkFormGuard} from './meld/form/meld-form/meld-link-form/meld-link-form.guard';
import {MeldLinkFormComponent} from './meld/form/meld-form/meld-link-form/meld-link-form.component';

const appRoutes: Routes = [
  {path: 'meld/posts', component: MeldListComponent, resolve: {posts: MeldListGuard}},
  {path: 'meld/post', component: MeldFormComponent},
  {path: 'meld/post/:id/image', component: MeldImageFormComponent, resolve: {post: MeldImageFormGuard}},
  {path: 'meld/post/image', component: MeldImageFormComponent, resolve: {post: MeldImageCreateGuard}},

  {path: 'meld/post/:id/photo', component: MeldPhotoFormComponent, resolve: {post: MeldPhotoFormGuard}},
  {path: 'meld/post/photo', component: MeldPhotoFormComponent, resolve: {post: MeldPhotoCreateGuard}},

  {path: 'meld/post/:id/text', component: MeldTextFormComponent, resolve: {post: MeldTextFormGuard}},
  {path: 'meld/post/text', component: MeldTextFormComponent, resolve: {post: MeldTextCreateGuard}},

  {path: 'meld/post/:id/youtube', component: MeldYoutubeFormComponent, resolve: {post: MeldYoutubeFormGuard}},
  {path: 'meld/post/youtube', component: MeldYoutubeFormComponent, resolve: {post: MeldYoutubeCreateGuard}},

  {path: 'meld/post/:id/link', component: MeldLinkFormComponent, resolve: {post: MeldLinkFormGuard}},
  {path: 'meld/post/link', component: MeldLinkFormComponent, resolve: {post: MeldLinkCreateGuard}}

];
export const appRoutingProviders: any[] = [
  MeldListGuard,
  MeldImageFormGuard,
  MeldImageCreateGuard,
  MeldPhotoFormGuard,
  MeldPhotoCreateGuard,
  MeldTextFormGuard,
  MeldTextCreateGuard,
  MeldYoutubeFormGuard,
  MeldYoutubeCreateGuard,
  MeldLinkFormGuard,
  MeldLinkCreateGuard
];
export const routing: ModuleWithProviders = RouterModule.forChild(appRoutes);
