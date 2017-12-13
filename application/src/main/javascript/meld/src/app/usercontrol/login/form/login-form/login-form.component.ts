import {Component} from '@angular/core';
import {Http, Response} from "@angular/http";
import {Router} from "@angular/router";
import {LoginForm} from "./login-form.interfaces";
import {LoginFormModel} from "./login-form.classes";
import {AppService} from '../../../../app.service';

@Component({
  selector: 'app-login-form',
  templateUrl: 'login-form.component.html',
  styleUrls: ['login-form.component.css']
})
export class LoginFormComponent {


  public login: LoginForm = new LoginFormModel();

  constructor(private http: Http,
              private router: Router,
              private appService: AppService) {
  }

  onSubmit() {
    const appService = this.appService;
    this.http.post('service/usercontrol/login/form', this.login)
      .subscribe((res: Response) => {
        if (res.status == 200) {
          this.login = res.json() as LoginForm;
          this.appService.load().then((() => {
            this.router.navigate([appService.redirectUrl]);
          }));
        }
      });

  }


}
