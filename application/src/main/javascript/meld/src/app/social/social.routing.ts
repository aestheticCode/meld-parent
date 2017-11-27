import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProfileComponent} from "./profile/profile.component";
import {ContactFormComponent} from "./profile/contact-form/contact-form.component";
import {ContactFormGuard} from "./profile/contact-form/contact-form.guard";
import {UserFormComponent} from "./profile/user-form/user-form.component";
import {EducationFormComponent} from "./profile/education-form/education-form.component";
import {PlacesFormComponent} from "./profile/places-form/places-form.component";
import {WorkHistoryFormComponent} from "./profile/work-history-form/work-history-form.component";
import {UserFormGuard} from "./profile/user-form/user.guard";
import {EducationFormGuard} from "./profile/education-form/education.guard";
import {UserViewComponent} from "./profile/user-form/user-view.component";
import {EducationViewComponent} from "./profile/education-form/education-view.component";
import {PlacesViewComponent} from "./profile/places-form/places-view.component";
import {PlacesFormGuard} from "./profile/places-form/places.guard";
import {WorkHistoryFormGuard} from "./profile/work-history-form/work-hisotry.guard";
import {WorkHistoryViewComponent} from "./profile/work-history-form/work-history-view.component";
import {ContactViewComponent} from "./profile/contact-form/contact-view.component";
import {ImageFormComponent} from "./profile/image-form/image-form.component";
import {PeopleComponent} from "./people/people.component";

const appRoutes: Routes = [
  {
    path : 'people',
    component : PeopleComponent
  },
  {
    path: 'profile',
    component: ProfileComponent,
    children: [
      {
        path: 'user/view',
        component: UserViewComponent,
        outlet: 'profile',
        resolve: {user: UserFormGuard}
      },
      {
        path: 'user/edit',
        component: UserFormComponent,
        outlet: 'profile',
        resolve: {user: UserFormGuard}
      },
      {
        path: 'education/view',
        component: EducationViewComponent,
        outlet: 'profile',
        resolve: {education: EducationFormGuard}
      },
      {
        path: 'education/edit',
        component: EducationFormComponent,
        outlet: 'profile',
        resolve: {education: EducationFormGuard}
      },
      {
        path: 'places/view',
        component: PlacesViewComponent,
        outlet: 'profile',
        resolve: {places: PlacesFormGuard}
      },
      {
        path: 'places/edit',
        component: PlacesFormComponent,
        outlet: 'profile',
        resolve: {places: PlacesFormGuard}
      },
      {
        path: 'work/history/view',
        component: WorkHistoryViewComponent,
        outlet: 'profile',
        resolve: {workHistory: WorkHistoryFormGuard}
      },
      {
        path: 'work/history/edit',
        component: WorkHistoryFormComponent,
        outlet: 'profile',
        resolve: {workHistory: WorkHistoryFormGuard}
      },
      {
        path: 'contact/view',
        component: ContactViewComponent,
        outlet: 'profile',
        resolve: {contact: ContactFormGuard}
      },
      {
        path: 'contact/edit',
        component: ContactFormComponent,
        outlet: 'profile',
        resolve: {contact: ContactFormGuard}
      },
      {
        path: '',
        component: ImageFormComponent,
        outlet: 'profile'
      }
    ]
  }
];
export const appRoutingProviders: any[] = [
  UserFormGuard,
  ContactFormGuard,
  EducationFormGuard,
  PlacesFormGuard,
  WorkHistoryFormGuard
];
export const routing: ModuleWithProviders = RouterModule.forChild(appRoutes);
