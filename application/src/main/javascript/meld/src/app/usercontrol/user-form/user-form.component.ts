import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, NgModel} from "@angular/forms";
import {Http, Response} from "@angular/http";
import {UserForm} from "./UserForm";
import {ActivatedRoute} from "@angular/router";
import {UserFormModel} from "./UserFormModel";

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
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { user: UserForm }) => {
      this.user = data.user || new UserFormModel();
    });

    this.email
      .control
      .setAsyncValidators((control: AbstractControl) => {
        return this.http.post('service/usercontrol/user/form/validate',
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
    this.http.post(`service/usercontrol/user/form`, this.user)
      .subscribe((res: Response) => {
        this.user = res.json();
      });
  }

  onUpdate() {
    this.http.put(`service/usercontrol/user/${this.user.id}/form`, this.user)
      .subscribe((res: Response) => {
        this.user = res.json();
      });
  }

}
