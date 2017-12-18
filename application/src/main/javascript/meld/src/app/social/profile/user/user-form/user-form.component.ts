import {Component, OnInit, ViewChild} from '@angular/core';
import {Http, Response} from '@angular/http';
import {ActivatedRoute, Router} from '@angular/router';
import {NgModel} from '@angular/forms';
import {UserForm} from '../user.interfaces';
import {UserFormModel} from '../user.classes';
import {Enum} from '../../../../../lib/pipe/meld-enum/meld-enum.interfaces';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-user-form',
  templateUrl: 'user-form.component.html',
  styleUrls: ['user-form.component.css']
})
export class UserFormComponent implements OnInit {

  public user: UserForm;

  @ViewChild('email')
  private email: NgModel;

  public genders: Enum[] = [{value: 'MALE', label: 'Male'}, {value: 'FEMALE', label: 'Female'}];

  constructor(private http: Http,
              private router: MeldRouterService) {
  }

  ngOnInit() {
    this.user = this.router.data.user || new UserFormModel();
  }

  onSave() {
    this.http.post(`service/social/user/current/form`, this.user)
      .subscribe((res: Response) => {
        this.user = res.json();
        this.router.navigate(['social', 'profile', this.router.param.id,{outlets: {profile: ['user', 'view']}}]);
      });
  }

  onUpdate() {
    this.http.put(`service/social/user/current/form`, this.user)
      .subscribe((res: Response) => {
        this.user = res.json();
        this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['user', 'view']}}]);
      });
  }

  onCancel() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['user', 'view']}}]);
  }

}
