import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {LoginForm} from './login-form.interfaces';
import {LoginFormModel} from './login-form.classes';
import {AppService} from '../../../../app.service';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-login-form',
  templateUrl: 'login-form.component.html',
  styleUrls: ['login-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LoginFormComponent implements OnInit {

  public login: LoginForm = new LoginFormModel();

  public loginError : boolean = false;

  @ViewChild("form")
  private form : NgForm;

  constructor(private http: HttpClient,
              private router: Router,
              private appService: AppService) {
  }

  ngOnInit(): void {
    this.form.valueChanges.subscribe((value) => {
      this.loginError = false;
    })
  }

  onSubmit() {
    const appService = this.appService;
    this.http.post<LoginForm>('service/usercontrol/login/form', this.login)
      .subscribe((res: LoginForm) => {
        this.login = res;
        this.appService.load().then((() => {
          this.router.navigate([appService.redirectUrl]);
        }));
      }, (error : HttpResponse<LoginForm>) => {
        this.loginError = true;
      });

  }


}
