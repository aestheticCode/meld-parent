import {Component, OnInit} from '@angular/core';
import {Chat} from "./chat-form/chat-form.interfaces";
import {ActivatedRoute, Router} from "@angular/router";
import {Http, Response} from "@angular/http";
import {PhoneModel} from "./phone-form/phone-form.classes";
import {Phone} from "./phone-form/phone-form.interfaces";
import {EmailModel} from "./email-form/email-form.classes";
import {Email} from "./email-form/email-form.interfaces";
import {ChatModel} from "./chat-form/chat-form.classes";
import {Contact} from '../contact-form.interfaces';
import {ContactModel} from '../contact-form.classes';
import {Strings} from '../../../../../lib/common/utils/Strings';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-social-contact-form',
  templateUrl: 'contact-form.component.html',
  styleUrls: ['contact-form.component.css']
})
export class ContactFormComponent implements OnInit {

  contact: Contact;

  constructor(private http: Http,
              private router : MeldRouterService) {
  }

  ngOnInit() {
    this.contact = this.router.data.contact || new ContactModel();
  }

  onCreatePhone() {
    this.contact.phones.push(new PhoneModel())
  }

  onDeletePhone(phone : Phone) {
    if (this.contact.phones.length > 1) {
      let indexOf = this.contact.phones.indexOf(phone);
      this.contact.phones.splice(indexOf, 1);
    }
  }

  onCreateEmail() {
    this.contact.emails.push(new EmailModel());
  }

  onDeleteEmail(email : Email) {
    if (this.contact.emails.length > 1) {
      let indexOf = this.contact.emails.indexOf(email);
      this.contact.emails.splice(indexOf, 1);
    }
  }

  onCreateChat() {
    this.contact.chats.push(new class implements Chat {
      name: string;
      type: string;
    })
  }

  onDeleteChat(chat : Chat) {
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
      this.contact.chats.push(new ChatModel())
    }
  }

  onSave() {
    this.filterEmptyPhones();
    this.filterEmptyEmails();
    this.filterEmptyChats();
    this.http.post("service/social/user/current/contact", this.contact)
      .subscribe((res: Response) => {
        this.contact = res.json();
        this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['user', 'view']}}]);
      })
  }

  onUpdate() {
    this.filterEmptyPhones();
    this.filterEmptyEmails();
    this.filterEmptyChats();
    this.http.put("service/social/user/current/contact", this.contact)
      .subscribe((res: Response) => {
        this.contact = res.json();
        this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['user', 'view']}}]);
      })
  }

  onCancel() {
    this.filterEmptyPhones();
    this.filterEmptyEmails();
    this.filterEmptyChats();
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['user', 'view']}}]);
  }

  private filterEmptyPhones() {
    this.contact.phones = this.contact.phones.filter((phone) => Strings.isNotEmpty(phone.type) && Strings.isNotEmpty(phone.number))
  }

  private filterEmptyEmails() {
    this.contact.emails = this.contact.emails.filter((email) => Strings.isNotEmpty(email.email))
  }

  private filterEmptyChats() {
    this.contact.chats = this.contact.chats.filter((chat) => Strings.isNotEmpty(chat.type) && Strings.isNotEmpty(chat.name))
  }

}
