import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Contact} from '../contact-form.interfaces';
import {ContactModel} from '../contact-form.classes';

@Component({
  selector: 'app-contact-view',
  templateUrl: './contact-view.component.html',
  styleUrls: ['./contact-view.component.css']
})
export class ContactViewComponent implements OnInit {

  contact: Contact;

  constructor(private http: Http,
              private route: ActivatedRoute,
              private router : Router) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { contact: Contact }) => {
      this.contact = data.contact || new ContactModel();
    });
  }


  onEdit() {
    this.router.navigate(['social', 'profile', {outlets: {profile: ['contact', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile']);
  }



}
