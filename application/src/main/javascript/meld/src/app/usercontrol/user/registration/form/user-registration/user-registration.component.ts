import {Component, ViewEncapsulation} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Router} from '@angular/router';
import {RegistrationForm} from './user-registration.interfaces';
import {RegistrationFormModel} from './user-registration.classes';
import {Strings} from 'lib/common/utils/Strings';
import {AppService} from '../../../../../app.service';

@Component({
  selector: 'app-user-registration',
  templateUrl: 'user-registration.component.html',
  styleUrls: ['user-registration.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class UserRegistrationComponent {

  public registration: RegistrationForm = new RegistrationFormModel();

  constructor(private http: Http,
              private router: Router,
              private appService: AppService) {
  }


  onSubmit() {
    const appService = this.appService;
    this.http.post('service/usercontrol/registration/form', this.registration)
      .subscribe((res: Response) => {
        if (res.status == 200) {
          this.registration = res.json() as RegistrationForm;
          this.appService.load().then((() => {
            this.router.navigate([appService.redirectUrl]);
          }));
        }
      });

  }

  get passwordsConfirm() : boolean {
    if (Strings.isEmpty(this.registration.password) || Strings.isEmpty(this.registration.confirm)) {
      return false;
    }

    return this.registration.password == this.registration.confirm;
  }


}
