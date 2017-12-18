import { Component, OnInit } from '@angular/core';
import {Http} from '@angular/http';
import {Contact} from '../contact-form.interfaces';
import {ContactModel} from '../contact-form.classes';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-contact-view',
  templateUrl: './contact-view.component.html',
  styleUrls: ['./contact-view.component.css']
})
export class ContactViewComponent implements OnInit {

  contact: Contact;

  constructor(private http: Http,
              private router: MeldRouterService) {
  }

  ngOnInit() {
    this.contact = this.router.data.contact || new ContactModel();
  }


  onEdit() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['contact', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile', this.router.param.id]);
  }




}
