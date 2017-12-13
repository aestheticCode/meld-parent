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

  @Input("readonly")
  public readonly: boolean = true;

  @Output("deleteClick")
  private deleteClick: EventEmitter<Company> = new EventEmitter();

  onDelete() {
    this.company.name = undefined;
    this.company.description = undefined;
    this.company.start = undefined;
    this.company.end = undefined;
    this.company.title = undefined;

    this.deleteClick.emit(this.company);
  }

}
