import {Component, Input, OnInit} from '@angular/core';
import {Education} from "./education.interfaces";
import {EducationModel} from "./education.classes";
import {ActivatedRoute, Router} from "@angular/router";
import {Http, Response} from "@angular/http";
import {Strings} from "../../../../lib/common/utils/Strings";
import {SchoolFormModel} from "./school-form/school-form.classes";
import {School} from "./school-form/school-form.interfaces";

@Component({
  selector: 'app-social-education-form',
  templateUrl: 'education-form.component.html',
  styleUrls: ['education-form.component.css']
})
export class EducationFormComponent implements OnInit {

  public education: Education;

  constructor(private http: Http,
              private route: ActivatedRoute,
              private router : Router) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { education: Education }) => {
      this.education = data.education || new EducationModel();
    });
  }

  onCreateSchool() {
    this.education.schools.push(new SchoolFormModel())
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

  onSave() {
    this.filterEmptySchools();
    this.http.post("service/social/user/current/education", this.education)
      .subscribe((res: Response) => {
        this.education = res.json();
        this.router.navigate(['social', 'profile', {outlets: {profile: ['education', 'view']}}]);
      })
  }

  onUpdate() {
    this.filterEmptySchools();
    this.http.put("service/social/user/current/education", this.education)
      .subscribe((res: Response) => {
        this.education = res.json();
        this.router.navigate(['social', 'profile', {outlets: {profile: ['education', 'view']}}]);
      })
  }

  onCancel() {
    this.filterEmptySchools();
    this.router.navigate(['social', 'profile', {outlets: {profile: ['education', 'view']}}]);
  }

  private filterEmptySchools() {
    this.education.schools
      = this.education.schools.filter((school) => {
      return Strings.isNotEmpty(school.name)
        || Strings.isNotEmpty(school.course)
        || Strings.isNotEmpty(school.start)
        || Strings.isNotEmpty(school.end)
    })
  }

}
