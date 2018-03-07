import {NgModule} from '@angular/core';
import {ProfileComponent} from './profile/profile.component';
import {LibModule} from '../../lib/lib.module';
import {CommonModule} from '@angular/common';
import {appRoutingProviders, routing} from './social.routing';
import {UserFormComponent} from './profile/user/form/user-form/user-form.component';
import {UserViewComponent} from './profile/user/form/user-view/user-view.component';
import {EducationFormComponent} from './profile/education/form/education-form/education-form.component';
import {PlacesFormComponent} from './profile/places/form/places-form/places-form.component';
import {WorkHistoryFormComponent} from './profile/work-history/form/work-history-form/work-history-form.component';
import {SchoolSortPipe} from './profile/education/school-sort.pipe';
import {AddressFormComponent} from './profile/places/form/places-form/address-form/address-form.component';
import {AddressesSortPipe} from './profile/places/addresses-sort.pipe';
import {EducationViewComponent} from './profile/education/form/education-view/education-view.component';
import {PlacesViewComponent} from './profile/places/form/places-view/places-view.component';
import {WorkHistoryViewComponent} from './profile/work-history/form/work-history-view/work-history-view.component';
import {CompanyFormComponent} from './profile/work-history/form/work-history-form/company-form/company-form.component';
import {CompanyViewComponent} from './profile/work-history/form/work-history-view/company-view/company-view.component';
import {CompanySortPipe} from './profile/work-history/company-sort.pipe';
import {ImageViewComponent} from './profile/image/image-view/image-view.component';
import {PeopleComponent} from './people/people.component';
import {FindViewComponent} from './people/find/table/find-view/find-view.component';
import {CategoriesFormComponent} from './people/category/list/category-list/categories-form.component';
import {FollowingViewComponent} from './people/find/table/following-view/following-view.component';
import {CategoryComponent} from './people/category/form/categories-form/category-view/category.component';
import {AddressMapComponent} from './profile/places/form/places-view/address-map/address-map.component';
import {AddressViewComponent} from './profile/places/form/places-view/address-view/address-view.component';
import {CategorySelectDialogComponent} from './people/category/select/category-select-dialog/category-select-dialog.component';

import {MediaModule} from '../media/media.module';
import {SchoolFormComponent} from './profile/education/form/education-form/school-form/school-form.component';
import {SchoolViewComponent} from './profile/education/form/education-view/school-view/school-view.component';
import { CategoryFormComponent } from './people/category/form/categories-form/category-form/category-form.component';
import { SchoolSelectComponent } from './profile/education/select/school-select/school-select.component';
import { AddressSelectComponent } from './profile/places/select/address-select/address-select.component';
import { CompanySelectComponent } from './profile/work-history/select/company-select/company-select.component';
import { CategoryMultiSelectComponent } from './people/category/select/category-multi-select/category-multi-select.component';
import {EducationDialogComponent} from './profile/education/form/education-form/education-dialog/education-dialog.component';
import { PlacesDialogComponent } from './profile/places/form/places-form/places-dialog/places-dialog.component';
import { UserDialogComponent } from './profile/user/form/user-form/user-dialog/user-dialog.component';
import { WorkHistoryDialogComponent } from './profile/work-history/form/work-history-form/work-history-dialog/work-history-dialog.component';
import { CommunitiesComponent } from './communities/communities.component';
import { FindViewComponent as FindViewComponent2 } from './communities/find/table/find-view/find-view.component';
import { RecommendedViewComponent } from './communities/find/table/recommended-view/recommended-view.component';
import { InvitationsViewComponent } from './communities/find/table/invitations-view/invitations-view.component';
import {ContactFormComponent} from './profile/contact/form/contact-form/contact-form.component';
import {ChatFormComponent} from './profile/contact/form/contact-form/chat-form/chat-form.component';
import {EmailFormComponent} from './profile/contact/form/contact-form/email-form/email-form.component';
import {PhoneFormComponent} from './profile/contact/form/contact-form/phone-form/phone-form.component';
import {ContactViewComponent} from './profile/contact/form/contact-view/contact-view.component';
import {ContactDialogComponent} from './profile/contact/form/contact-form/contact-dialog/contact-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    LibModule,
    MediaModule,
    routing
  ],
  declarations: [
    ContactFormComponent,
    ChatFormComponent,
    EmailFormComponent,
    PhoneFormComponent,
    ProfileComponent,
    UserFormComponent,
    UserViewComponent,
    EducationFormComponent,
    PlacesFormComponent,
    WorkHistoryFormComponent,
    SchoolFormComponent,
    SchoolSortPipe,
    AddressFormComponent,
    AddressesSortPipe,
    EducationViewComponent,
    SchoolViewComponent,
    PlacesViewComponent,
    WorkHistoryViewComponent,
    CompanyFormComponent,
    CompanyViewComponent,
    CompanySortPipe,
    ContactViewComponent,
    ImageViewComponent,
    PeopleComponent,
    FindViewComponent,
    CategoriesFormComponent,
    FollowingViewComponent,
    CategoryComponent,
    AddressMapComponent,
    AddressViewComponent,
    CategorySelectDialogComponent,
    CategoryFormComponent,
    SchoolSelectComponent,
    AddressSelectComponent,
    CompanySelectComponent,
    EducationDialogComponent,
    CategoryMultiSelectComponent,
    ContactDialogComponent,
    PlacesDialogComponent,
    UserDialogComponent,
    WorkHistoryDialogComponent,
    CommunitiesComponent,
    FindViewComponent2,
    RecommendedViewComponent,
    InvitationsViewComponent,
  ],
  exports: [
    ContactFormComponent,
    ChatFormComponent,
    EmailFormComponent,
    PhoneFormComponent,
    ProfileComponent,
    UserFormComponent,
    UserViewComponent,
    EducationFormComponent,
    PlacesFormComponent,
    WorkHistoryFormComponent,
    SchoolFormComponent,
    SchoolSortPipe,
    AddressFormComponent,
    AddressesSortPipe,
    EducationViewComponent,
    SchoolViewComponent,
    PlacesViewComponent,
    WorkHistoryViewComponent,
    CompanyFormComponent,
    CompanyViewComponent,
    CompanySortPipe,
    ContactViewComponent,
    ImageViewComponent,
    PeopleComponent,
    FindViewComponent,
    CategoriesFormComponent,
    FollowingViewComponent,
    CategoryComponent,
    AddressMapComponent,
    AddressViewComponent,
    CategorySelectDialogComponent,
    ContactDialogComponent,
    PlacesDialogComponent,
    UserDialogComponent,
    WorkHistoryDialogComponent,
    FindViewComponent2,
    RecommendedViewComponent,
    InvitationsViewComponent,
  ],
  entryComponents: [
    CategorySelectDialogComponent,
    EducationDialogComponent,
    ContactDialogComponent,
    PlacesDialogComponent,
    UserDialogComponent,
    WorkHistoryDialogComponent
  ],
  providers : [
    appRoutingProviders
  ]
})
export class SocialModule {
}
