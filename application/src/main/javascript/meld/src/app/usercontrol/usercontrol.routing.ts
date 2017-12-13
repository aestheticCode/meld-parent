import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserTableComponent} from './user/table/user-table.component';
import {UserTableGuard} from './user/table/user-table.guard';
import {UserViewComponent} from './user/form/user-view/user-view.component';
import {UserCreateGuard, UserEditGuard} from './user/form/user.guard';
import {UserFormComponent} from './user/form/user-form/user-form.component';
import {LoginFormComponent} from './login/form/login-form/login-form.component';
import {UserRegistrationComponent} from './user/registration/user-registration.component';
import {LogoutFormComponent} from './logout/form/logout-form/logout-form.component';
import {RoleTableComponent} from './role/table/role-table/role-table.component';
import {RoleTableGuard} from './role/table/role-table/role-table.guard';
import {RoleFormComponent} from './role/form/role-form/role-form.component';
import {RoleFormGuard} from './role/form/role-form/role-form.guard';
import {GroupTableComponent} from './group/table/group-table/group-table.component';
import {GroupTableGuard} from './group/table/group-table/group-table.guard';
import {GroupFormComponent} from './group/form/group-form/group-form.component';
import {GroupFormGuard} from './group/form/group-form/group-form.guard';

const appRoutes: Routes = [
  {path: 'users', component: UserTableComponent, resolve: {users: UserTableGuard}},
  {path: 'user/:id/view', component: UserViewComponent, resolve: {user: UserEditGuard}},
  {path: 'user/:id/edit', component: UserFormComponent, resolve: {user: UserEditGuard}},
  {path: 'user', component: UserFormComponent, resolve: {user: UserCreateGuard}},
  {path: 'login', component: LoginFormComponent},
  {path: 'registration', component: UserRegistrationComponent},
  {path: 'logout', component: LogoutFormComponent},
  {path: 'roles', component: RoleTableComponent, resolve: {roles: RoleTableGuard}},
  {path: 'role/:id', component: RoleFormComponent, resolve: {role: RoleFormGuard}},
  {path: 'role', component: RoleFormComponent},
  {path: 'groups', component: GroupTableComponent, resolve: {groups: GroupTableGuard}},
  {path: 'group/:id', component: GroupFormComponent, resolve: {group: GroupFormGuard}},
  {path: 'group', component: GroupFormComponent}
];
export const appRoutingProviders: any[] = [
  UserTableGuard,
  UserEditGuard,
  UserCreateGuard,
  GroupTableGuard,
  GroupFormGuard,
  RoleTableGuard,
  RoleFormGuard
];
export const routing: ModuleWithProviders = RouterModule.forChild(appRoutes);
