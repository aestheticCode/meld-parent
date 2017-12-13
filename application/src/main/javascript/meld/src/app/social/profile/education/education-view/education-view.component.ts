import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Http} from '@angular/http';
import {Education} from '../education.interfaces';
import {EducationModel} from '../education.classes';

@Component({
  selector: 'app-social-education-view',
  templateUrl: 'education-view.component.html',
  styleUrls: ['education-view.component.css']
})
export class EducationViewComponent implements OnInit {

  public education: Education;

  constructor(private http: Http,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { education: Education }) => {
      this.education = data.education || new EducationModel();
    });
  }

  onEdit() {
    this.router.navigate(['social', 'profile', {outlets: {profile: ['education', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile']);
  }


}
