import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Http} from '@angular/http';
import {Contact} from '../../contact.interfaces';
import {MeldRouterService} from '../../../../../../lib/service/meld-router/meld-router.service';
import {mobileTypes} from '../../contact.classes';

@Component({
  selector: 'app-contact-view',
  templateUrl: './contact-view.component.html',
  styleUrls: ['./contact-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class ContactViewComponent implements OnInit {

  contact: Contact;

  constructor(private http: Http,
              private router: MeldRouterService) {
  }

  ngOnInit() {
    this.contact = this.router.data.contact;
  }


  onEdit() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['contact', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile', this.router.param.id]);
  }

  mobiles() {
    return mobileTypes();
  }



}
