import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {UserForm} from '../user.interfaces';
import {UserFormModel} from '../user.classes';

@Component({
  selector: 'app-user-view',
  templateUrl: 'user-view.component.html',
  styleUrls: ['user-view.component.css']
})
export class UserViewComponent implements OnInit {

  public user: UserForm;

  public readonly: boolean = true;

  constructor(private http: Http,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { user: UserForm }) => {
      this.user = data.user || new UserFormModel();
    });
  }

  onEditClick() {
    this.router.navigate(['social', 'profile', {outlets: {profile: ['user', 'edit']}}]);
  }

  onCancelClick() {
    this.router.navigate(['social', 'profile']);
  }

}
