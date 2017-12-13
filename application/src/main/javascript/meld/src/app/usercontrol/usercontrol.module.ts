import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LogoutFormComponent} from './logout/form/logout-form/logout-form.component';
import {LoginFormComponent} from './login/form/login-form/login-form.component';
import {appRoutingProviders, routing} from './usercontrol.routing';
import {LibModule} from '../../lib/lib.module';
import {UserTableComponent} from './user/table/user-table.component';
import {UserFormComponent} from './user/form/user-form/user-form.component';
import {RoleMultiselectComponent} from './role/multiselect/role-multiselect/role-multiselect.component';
import {GroupMultiselectComponent} from './group/multiselect/group-multiselect/group-multiselect.component';
import {GroupTableComponent} from './group/table/group-table/group-table.component';
import {GroupFormComponent} from './group/form/group-form/group-form.component';
import {RoleFormComponent} from './role/form/role-form/role-form.component';
import {RoleTableComponent} from './role/table/role-table/role-table.component';
import {UserViewComponent} from './user/form/user-view/user-view.component';
import {UserRegistrationComponent} from './user/registration/user-registration.component';

@NgModule({
  imports: [
    CommonModule,
    LibModule,
    routing
  ],
  declarations: [
    LoginFormComponent,
    LogoutFormComponent,
    UserTableComponent,
    UserFormComponent,
    RoleMultiselectComponent,
    GroupMultiselectComponent,
    GroupTableComponent,
    GroupFormComponent,
    RoleFormComponent,
    RoleTableComponent,
    UserViewComponent,
    UserRegistrationComponent
  ],
  entryComponents : [
    UserFormComponent
  ],
  providers: [
    appRoutingProviders
  ]
})
export class UserControlModule {
}
