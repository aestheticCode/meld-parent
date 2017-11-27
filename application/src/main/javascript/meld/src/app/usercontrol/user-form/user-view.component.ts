import {Component, OnInit} from '@angular/core';
import {UserFormModel} from "./user.classes";
import {UserForm} from "./user.interfaces";
import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";

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
    this.router.navigate(['usercontrol/user', this.user.id, 'edit']);
  }

  onCancelClick() {
    this.router.navigate(['usercontrol/users']);
  }

}
