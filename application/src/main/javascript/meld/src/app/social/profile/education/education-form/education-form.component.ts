import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Education} from '../education.interfaces';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {AbstractForm} from '../../../../../lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {Link} from '../../../../../lib/common/rest/Link';
import {MatDialog} from '@angular/material';
import {EducationDialogComponent} from './education-dialog/education-dialog.component';

@Component({
  selector: 'app-social-education-form',
  templateUrl: 'education-form.component.html',
  styleUrls: ['education-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class EducationFormComponent extends AbstractForm<Education> implements OnInit {

  public education: FormGroup;

  public links: Link[];

  constructor(private http: HttpClient,
              private builder: FormBuilder,
              private dialog : MatDialog,
              private router: MeldRouterService) {
    super();
  }

  ngOnInit() {
    let education: Education = this.router.data.education;

    this.links = education.links;

    this.education = this.builder.group({
      id : this.builder.control(education.id),
      categories : this.builder.control(education.categories),
      schools: this.builder.array(education.schools.map((school) => {
        return this.builder.group({
          id: this.builder.control(school.id),
          name: this.builder.control(school.name),
          location: this.builder.control(school.location),
          course: this.builder.control(school.course),
          startYear: this.builder.group(school.startYear),
          endYear: this.builder.group(school.endYear),
          visitStart: this.builder.group(school.visitStart),
          visitEnd: this.builder.group(school.visitEnd),
          tillNow: this.builder.control(school.tillNow)
        })
      }))
    });
  }

  get schools() {
    return this.education.get('schools') as FormArray;
  }

  onCreateSchool() {
    this.schools.push(this.builder.group({
      id: this.builder.control(''),
      name: this.builder.control(''),
      location: this.builder.control(null),
      course: this.builder.control(''),
      startYear: this.builder.group({
        year: this.builder.control(null),
        semester: this.builder.control('')
      }),
      endYear: this.builder.group({
        year: this.builder.control(null),
        semester: this.builder.control('')
      }),
      visitStart: this.builder.group({
        year: this.builder.control(null),
        semester: this.builder.control('')
      }),
      visitEnd: this.builder.group({
        year: this.builder.control(null),
        semester: this.builder.control('')
      }),
      tillNow: this.builder.control(false)
    }));
  }

  onDeleteSchool(index: number) {
    this.schools.removeAt(index);
  }

  onVisibility() {
    this.dialog.open(EducationDialogComponent, {data : this.education, width : 400 + 'px'});
  }

  public preRequest(): boolean {
    return this.validateAllFields(this.education);
  }

  public saveRequest(): Observable<Education> {
    return this.http.post<Education>('service/social/user/current/education', this.education.getRawValue());
  }

  public updateRequest(): Observable<Education> {
    return this.http.put<Education>('service/social/user/current/education', this.education.getRawValue());
  }

  public deleteRequest(): Observable<Education> {
    return this.http.delete<Education>('service/social/user/current/education');
  }

  public postRequest() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['education', 'view']}}]);
  }

}
