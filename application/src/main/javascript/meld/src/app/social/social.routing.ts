import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProfileComponent} from "./profile/profile.component";
import {ContactFormComponent} from "./profile/contact-form/contact-form.component";
import {ContactFormGuard} from "./profile/contact-form/contact-form.guard";
import {UserFormComponent} from "./profile/user-form/user-form.component";
import {UserFormGuard} from "./profile/user-form/user-form.guard";
import {EducationFormComponent} from "./profile/education-form/education-form.component";
import {PlacesFormComponent} from "./profile/places-form/places-form.component";
import {WorkHistoryFormComponent} from "./profile/work-history-form/work-history-form.component";
import {EducationFormGuard} from "./profile/education-form/education-form.guard";
import {PlacesFormGuard} from "./profile/places-form/places-form.guard";

const appRoutes: Routes = [
  {
    path: 'profile',
    component: ProfileComponent,
    children: [
      {
        path: 'user',
        component: UserFormComponent,
        outlet: 'profile',
        resolve: {user: UserFormGuard}
      },
      {
        path: 'education',
        component: EducationFormComponent,
        outlet: 'profile',
        resolve: {education: EducationFormGuard}
      },
      {
        path: 'places',
        component: PlacesFormComponent,
        outlet: 'profile',
        resolve: {places: PlacesFormGuard}
      },
      {
        path: 'work-history',
        component: WorkHistoryFormComponent,
        outlet: 'profile'
      },
      {
        path: 'contact',
        component: ContactFormComponent,
        outlet: 'profile',
        resolve: {contact: ContactFormGuard}
      }
    ]
  }
];
export const appRoutingProviders: any[] = [
  UserFormGuard,
  ContactFormGuard,
  EducationFormGuard,
  PlacesFormGuard
];
export const routing: ModuleWithProviders = RouterModule.forChild(appRoutes);
