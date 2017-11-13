import {NgModule} from '@angular/core';
import {ContactFormComponent} from "./profile/contact-form/contact-form.component";
import {ChatFormComponent} from "./profile/contact-form/chat-form/chat-form.component";
import {EmailFormComponent} from "./profile/contact-form/email-form/email-form.component";
import {PhoneFormComponent} from "./profile/contact-form/phone-form/phone-form.component";
import {ProfileComponent} from './profile/profile.component';
import {LibModule} from "../../lib/lib.module";
import {CommonModule} from "@angular/common";
import {appRoutingProviders, routing} from "./social.routing";
import { UserFormComponent } from './profile/user-form/user-form.component';
import { EducationFormComponent } from './profile/education-form/education-form.component';
import { PlacesFormComponent } from './profile/places-form/places-form.component';
import { WorkHistoryFormComponent } from './profile/work-history-form/work-history-form.component';
import { SchoolFormComponent } from './profile/education-form/school-form/school-form.component';
import { SchoolSortPipe } from './profile/education-form/school-sort.pipe';
import { AddressFormComponent } from './profile/places-form/address-form/address-form.component';
import { AddressesSortPipe } from './profile/places-form/addresses-sort.pipe';

@NgModule({
  imports: [
    CommonModule,
    LibModule,
    routing
  ],
  declarations: [
    ContactFormComponent,
    ChatFormComponent,
    EmailFormComponent,
    PhoneFormComponent,
    ProfileComponent,
    UserFormComponent,
    EducationFormComponent,
    PlacesFormComponent,
    WorkHistoryFormComponent,
    SchoolFormComponent,
    SchoolSortPipe,
    AddressFormComponent,
    AddressesSortPipe
  ],
  providers : [
    appRoutingProviders
  ]
})
export class SocialModule {
}
