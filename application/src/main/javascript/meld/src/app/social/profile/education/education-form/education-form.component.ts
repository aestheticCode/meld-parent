import {Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {Strings} from 'lib/common/utils/Strings';
import {Education} from '../education.interfaces';
import {School} from '../school-form.interfaces';
import {Objects} from 'lib/common/utils/Objects';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {SchoolFormModel} from '../school-form.classes';
import {AbstractForm} from '../../../../../lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SchoolFormComponent} from './school-form/school-form.component';

@Component({
  selector: 'app-social-education-form',
  templateUrl: 'education-form.component.html',
  styleUrls: ['education-form.component.css']
})
export class EducationFormComponent extends AbstractForm<Education> implements OnInit {

  @ViewChildren("schools")
  public schools : QueryList<SchoolFormComponent>;

  public education: Education;

  private router: MeldRouterService;

  constructor(http: HttpClient,
              router: MeldRouterService) {
    super(http);
    this.router = router;
  }

  ngOnInit() {
    this.education = this.router.data.education;
  }

  onCreateSchool() {
    this.education.schools.push(new SchoolFormModel());
  }

  onDeleteSchool(school: School) {
    if (this.education.schools.length > 0) {
      let indexOf = this.education.schools.indexOf(school);
      this.education.schools.splice(indexOf, 1);
    }
  }

  onEdit() {
    if (this.education.schools.length === 0) {
      this.education.schools.push(new SchoolFormModel());
    }
  }

  public saveRequest(): Observable<Education> {
    return this.http.post<Education>('service/social/user/current/education', this.education);
  }

  public updateRequest(): Observable<Education> {
    return this.http.put<Education>('service/social/user/current/education', this.education);
  }

  public deleteRequest(): Observable<Education> {
    return this.http.delete<Education>('service/social/user/current/education');
  }

  public preRequest() {
    return this.schools.filter((schoolComponent) => {
      for (let property in schoolComponent.form.controls) {
        schoolComponent.form.controls[property].markAsTouched();
      }
      return ! schoolComponent.form.valid
    }).length === 0
  }

  public postRequest() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['education', 'view']}}]);
  }


}
