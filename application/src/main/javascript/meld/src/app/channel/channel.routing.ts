import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MeldListComponent} from "./meld-list/meld-list.component";
import {MeldListGuard} from "./meld-list/meld-list.guard";
import {MeldFormComponent} from "./meld-form/meld-form.component";
import {MeldFormGuard} from "./meld-form/meld-form.guard";

const appRoutes: Routes = [
  {path: 'meld/posts', component: MeldListComponent, resolve: {posts: MeldListGuard}},
  {path: 'meld/post', component: MeldFormComponent},
  {path: 'meld/post/:id', component: MeldFormComponent, resolve: {post: MeldFormGuard}}
];
export const appRoutingProviders: any[] = [
  MeldListGuard,
  MeldFormGuard
];
export const routing: ModuleWithProviders = RouterModule.forChild(appRoutes);
