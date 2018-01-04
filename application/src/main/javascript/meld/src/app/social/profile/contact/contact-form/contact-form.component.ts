import {Component, OnInit} from '@angular/core';
import {Chat} from './chat-form/chat-form.interfaces';
import {PhoneModel} from './phone-form/phone-form.classes';
import {Phone} from './phone-form/phone-form.interfaces';
import {EmailModel} from './email-form/email-form.classes';
import {Email} from './email-form/email-form.interfaces';
import {ChatModel} from './chat-form/chat-form.classes';
import {Contact} from '../contact.interfaces';
import {Strings} from 'lib/common/utils/Strings';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {AbstractForm} from 'lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'app-social-contact-form',
  templateUrl: 'contact-form.component.html',
  styleUrls: ['contact-form.component.css']
})
export class ContactFormComponent extends AbstractForm<Contact> implements OnInit {

  public contact: Contact;
  private router: MeldRouterService;

  constructor(http: HttpClient,
              router: MeldRouterService) {
    super(http);
    this.router = router;
  }

  ngOnInit() {
    this.contact = this.router.data.contact;
  }

  onCreatePhone() {
    this.contact.phones.push(new PhoneModel());
  }

  onDeletePhone(phone: Phone) {
    if (this.contact.phones.length > 1) {
      let indexOf = this.contact.phones.indexOf(phone);
      this.contact.phones.splice(indexOf, 1);
    }
  }

  onCreateEmail() {
    this.contact.emails.push(new EmailModel());
  }

  onDeleteEmail(email: Email) {
    if (this.contact.emails.length > 1) {
      let indexOf = this.contact.emails.indexOf(email);
      this.contact.emails.splice(indexOf, 1);
    }
  }

  onCreateChat() {
    this.contact.chats.push(new class implements Chat {
      name: string;
      type: string;
    });
  }

  onDeleteChat(chat: Chat) {
    if (this.contact.chats.length > 1) {
      let indexOf = this.contact.chats.indexOf(chat);
      this.contact.chats.splice(indexOf, 1);
    }
  }

  onEdit() {
    if (this.contact.phones.length === 0) {
      this.contact.phones.push(new PhoneModel());
    }
    if (this.contact.emails.length === 0) {
      this.contact.emails.push(new EmailModel());
    }
    if (this.contact.chats.length === 0) {
      this.contact.chats.push(new ChatModel());
    }
  }

  public preRequest() {
    this.filterEmptyPhones();
    this.filterEmptyEmails();
    this.filterEmptyChats();
  }

  public postRequest(form : Contact) {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['contact', 'view']}}]);
  }

  public saveRequest(): Observable<Contact> {
    return this.http.post<Contact>('service/social/user/current/contact', this.contact)
  }

  public updateRequest(): Observable<Contact> {
    return this.http.put<Contact>('service/social/user/current/contact', this.contact)
  }

  public deleteRequest(): Observable<Contact> {
    return this.http.delete<Contact>('service/social/user/current/contact')
  }

  private filterEmptyPhones() {
    this.contact.phones = this.contact.phones.filter((phone) => Strings.isNotEmpty(phone.number));
  }

  private filterEmptyEmails() {
    this.contact.emails = this.contact.emails.filter((email) => Strings.isNotEmpty(email.email));
  }

  private filterEmptyChats() {
    this.contact.chats = this.contact.chats.filter((chat) => Strings.isNotEmpty(chat.type) && Strings.isNotEmpty(chat.name));
  }

}
