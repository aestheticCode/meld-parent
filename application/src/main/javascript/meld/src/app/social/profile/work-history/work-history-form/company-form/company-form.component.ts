import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Company} from '../../company.interfaces';

@Component({
  selector: 'app-social-company-form',
  templateUrl: 'company-form.component.html',
  styleUrls: ['company-form.component.css']
})
export class CompanyFormComponent {

  @Input("company")
  public company: Company;

}
