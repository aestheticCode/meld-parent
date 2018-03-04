import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Contact} from '../contact.interfaces';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {AbstractForm} from 'lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Link} from '../../../../../lib/common/rest/Link';
import {ContactDialogComponent} from './contact-dialog/contact-dialog.component';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-social-contact-form',
  templateUrl: 'contact-form.component.html',
  styleUrls: ['contact-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ContactFormComponent extends AbstractForm<Contact> implements OnInit {

  public links: Link[];

  public contact: FormGroup;

  constructor(private http: HttpClient,
              private dialog : MatDialog,
              private builder: FormBuilder,
              private router: MeldRouterService) {
    super();
  }

  ngOnInit() {
    let contact = this.router.data.contact;

    this.links = contact.links;

    this.contact = this.builder.group({
      categories : this.builder.control(contact.categories),
      chats: this.builder.array(contact.chats.map((contact) => this.builder.group(contact))),
      emails: this.builder.array(contact.emails.map((email) => this.builder.group(email))),
      phones: this.builder.array(contact.phones.map((phone) => this.builder.group(phone)))
    });
  }

  get phones() {
    return this.contact.get('phones') as FormArray;
  }

  onCreatePhone() {
    this.phones.push(this.builder.group({
      number: this.builder.control('')
    }));
  }

  onDeletePhone(index: number) {
    this.phones.removeAt(index);
  }


  get emails() {
    return this.contact.get('emails') as FormArray;
  }

  onCreateEmail() {
    this.emails.push(this.builder.group({
      email: this.builder.control('', [Validators.required])
    }));

  }

  onDeleteEmail(index: number) {
    this.emails.removeAt(index);
  }

  get chats() {
    return this.contact.get('chats') as FormArray;
  }


  onCreateChat() {
    this.chats.push(this.builder.group({
      name: this.builder.control(''),
      type: this.builder.control('')
    }));
  }

  onDeleteChat(index: number) {
    this.chats.removeAt(index);
  }

  onVisibility() {
    this.dialog.open(ContactDialogComponent, {data: this.contact, width: 400 + 'px'});
  }

  public preRequest(): boolean {
    return this.validateAllFields(this.contact);
  }

  public postRequest(form: Contact) {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['contact', 'view']}}]);
  }

  public saveRequest(): Observable<Contact> {
    return this.http.post<Contact>('service/social/user/current/contact', this.contact.getRawValue());
  }

  public updateRequest(): Observable<Contact> {
    return this.http.put<Contact>('service/social/user/current/contact', this.contact.getRawValue());
  }

  public deleteRequest(): Observable<Contact> {
    return this.http.delete<Contact>('service/social/user/current/contact');
  }


}
