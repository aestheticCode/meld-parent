import {Component, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, NgModel} from "@angular/forms";
import {Http, Response} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {UserForm} from '../user.interfaces';
import {UserFormModel} from '../user.classes';

@Component({
  selector: 'app-user-form',
  templateUrl: 'user-form.component.html',
  styleUrls: ['user-form.component.css']
})
export class UserFormComponent implements OnInit {

  public user: UserForm;

  @ViewChild('email')
  private email: NgModel;

  public genders = [{value : 'MALE', label : 'Male'}, {value : 'FEMALE', label : 'Female'}];

  constructor(private http: Http,
              private route: ActivatedRoute,
              private router : Router) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { user: UserForm }) => {
      this.user = data.user || new UserFormModel();
    });
  }

  onSave() {
    this.http.post(`service/usercontrol/user/form`, this.user)
      .subscribe((res: Response) => {
        this.user = res.json();
        this.router.navigate(['usercontrol/user', this.user.id, 'view']);
      });
  }

  onUpdate() {
    this.http.put(`service/usercontrol/user/${this.user.id}/form`, this.user)
      .subscribe((res: Response) => {
        this.user = res.json();
        this.router.navigate(['usercontrol/user', this.user.id, 'view']);
      });
  }

  onCancel() {
    this.router.navigate(['usercontrol/user', this.user.id, 'view']);
  }

}
