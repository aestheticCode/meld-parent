import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProfileComponent} from "./profile/profile.component";
import {ContactFormGuard} from "./profile/contact/contact.guard";
import {PeopleComponent} from "./people/people.component";
import {CategoriesFormGuard} from './people/category/list/category-list/categories-form.guard';
import {FindViewComponent} from './people/find/table/find-view/find-view.component';
import {CategoriesFormComponent} from './people/category/list/category-list/categories-form.component';
import {FollowingViewComponent} from './people/find/table/following-view/following-view.component';
import {UserViewComponent} from './profile/user/form/user-view/user-view.component';
import {UserFormGuard} from './profile/user/user.guard';
import {UserFormComponent} from './profile/user/form/user-form/user-form.component';
import {EducationViewComponent} from './profile/education/form/education-view/education-view.component';
import {EducationFormGuard} from './profile/education/education.guard';
import {EducationFormComponent} from './profile/education/form/education-form/education-form.component';
import {PlacesViewComponent} from './profile/places/form/places-view/places-view.component';
import {PlacesFormGuard} from './profile/places/places.guard';
import {PlacesFormComponent} from './profile/places/form/places-form/places-form.component';
import {WorkHistoryViewComponent} from './profile/work-history/form/work-history-view/work-history-view.component';
import {WorkHistoryFormGuard} from './profile/work-history/work-hisotry.guard';
import {WorkHistoryFormComponent} from './profile/work-history/form/work-history-form/work-history-form.component';
import {ImageViewComponent} from './profile/image/image-view/image-view.component';
import {ImageGuard} from './profile/image/image.guard';
import {ProfileGuard} from './profile/profile.guard';
import {CategoryFormComponent} from './people/category/form/categories-form/category-form/category-form.component';
import {CategoryCreateGuard, CategoryFormGuard} from './people/category/form/categories-form/category-form/category-form.guard';
import {CommunitiesComponent} from './communities/communities.component';
import {FindViewComponent as FindViewComponent2} from './communities/find/table/find-view/find-view.component';
import {InvitationsViewComponent} from './communities/find/table/invitations-view/invitations-view.component';
import {RecommendedViewComponent} from './communities/find/table/recommended-view/recommended-view.component';
import {ContactViewComponent} from './profile/contact/form/contact-view/contact-view.component';
import {ContactFormComponent} from './profile/contact/form/contact-form/contact-form.component';

const appRoutes: Routes = [
  {
    path : 'communities',
    component : CommunitiesComponent,
    children: [
      {
        path : 'find',
        outlet: 'communities',
        component : FindViewComponent2
      },
      {
        path : 'invitations',
        outlet: 'communities',
        component : InvitationsViewComponent
      },
      {
        path : 'recommended',
        outlet: 'communities',
        component : RecommendedViewComponent
      },
      {
        path: '',
        outlet: 'communities',
        redirectTo : 'find'
      }
    ]
  },
  {
    path : 'people',
    component : PeopleComponent,
    children: [
      {
        path: 'categories',
        component: CategoriesFormComponent,
        outlet: 'people',
        resolve: {container: CategoriesFormGuard}
      },
      {
        path : 'category/:id',
        component : CategoryFormComponent,
        outlet : 'people',
        resolve : {category : CategoryFormGuard}
      },
      {
        path : 'category',
        component : CategoryFormComponent,
        outlet : 'people',
        resolve : {category : CategoryCreateGuard}
      },
      {
        path: 'find',
        component: FindViewComponent,
        outlet: 'people'
      },
      {
        path: 'following',
        component: FollowingViewComponent,
        outlet: 'people'
      },
      {
        path: '',
        outlet: 'people',
        redirectTo : 'find'
      }
    ]
  },
  {
    path: 'profile/:id',
    component: ProfileComponent,
    resolve: {profile: ProfileGuard},
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
        path: 'image/view',
        component: ImageViewComponent,
        outlet: 'profile',
        resolve: {profile: ImageGuard}
      },
      {
        path: '',
        redirectTo : 'image/view',
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
  WorkHistoryFormGuard,
  CategoriesFormGuard,
  ImageGuard,
  ProfileGuard,
  CategoryFormGuard,
  CategoryCreateGuard
];
export const routing: ModuleWithProviders = RouterModule.forChild(appRoutes);
