import {Component, ViewEncapsulation} from '@angular/core';
import {Router} from "@angular/router";
import {Http, Response} from "@angular/http";
import {AppService} from '../../../../app.service';

@Component({
  selector: 'app-logout-form',
  templateUrl: 'logout-form.component.html',
  styleUrls: ['logout-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LogoutFormComponent {

  constructor(private http: Http,
              private router: Router,
              private appService: AppService) {
  }

  onSubmit() {
    this.http.get('service/usercontrol/logout/form')
      .subscribe((res: Response) => {
        if (res.status == 200) {
          this.appService.load().then((() => {
            this.router.navigate(['/']);
          }));
        }
      });
  }

}
