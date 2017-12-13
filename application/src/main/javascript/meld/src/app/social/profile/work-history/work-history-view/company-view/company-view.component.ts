import {Component, Input, OnInit} from '@angular/core';
import {Company} from '../../company.interfaces';

@Component({
  selector: 'app-social-company-view',
  templateUrl: 'company-view.component.html',
  styleUrls: ['company-view.component.css']
})
export class CompanyViewComponent implements OnInit {

  @Input("company")
  company : Company;

  constructor() { }

  ngOnInit() {
  }

}
