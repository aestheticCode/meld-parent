import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Http} from '@angular/http';
import {Education} from '../../education.interfaces';
import {MeldRouterService} from '@aestheticcode/meld-lib';

@Component({
  selector: 'app-social-education-view',
  templateUrl: 'education-view.component.html',
  styleUrls: ['education-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class EducationViewComponent implements OnInit {

  public education: Education;

  constructor(private router: MeldRouterService) {}

  ngOnInit() {
    this.education = this.router.data.education;
  }

  onEdit() {
    this.router.navigate(['social', 'profile', this.router.param.id,{outlets: {profile: ['education', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile', this.router.param.id]);
  }


}
