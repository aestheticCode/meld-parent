import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {UserForm} from '../user.interfaces';
import {UserFormModel} from '../user.classes';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-user-view',
  templateUrl: 'user-view.component.html',
  styleUrls: ['user-view.component.css']
})
export class UserViewComponent implements OnInit {

  public user: UserForm;

  public genders = [{value : 'MALE', label : 'Male'}, {value : 'FEMALE', label : 'Female'}];

  constructor(public router: MeldRouterService) {}

  ngOnInit() {
    this.user = this.router.data.user || new UserFormModel();
  }

  onEditClick() {
    this.router.navigate(['social', 'profile', this.router.param.id,{outlets: {profile: ['user', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile', this.router.param.id]);
  }

}
