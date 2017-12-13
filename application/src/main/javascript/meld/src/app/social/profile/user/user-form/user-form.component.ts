import {Component, OnInit, ViewChild} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {AbstractControl, NgModel} from "@angular/forms";
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

  constructor(private http: Http,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { user: UserForm }) => {
      this.user = data.user || new UserFormModel();
    });

    this.email
      .control
      .setAsyncValidators((control: AbstractControl) => {
        return this.http.post('service/social/user/current/form/validate',
          {name: control.value, id: this.user.id})
          .map((res: Response) => {
            if (res.json()) {
              return null;
            } else {
              return {nonUnique: true}
            }
          })
      });

  }

  onSave() {
    this.http.post(`service/social/user/current/form`, this.user)
      .subscribe((res: Response) => {
        this.user = res.json();
        this.router.navigate(['social', 'profile', {outlets: {profile: ['user', 'view']}}]);
      });
  }

  onUpdate() {
    this.http.put(`service/social/user/current/form`, this.user)
      .subscribe((res: Response) => {
        this.user = res.json();
        this.router.navigate(['social', 'profile', {outlets: {profile: ['user', 'view']}}]);
      });
  }

}
