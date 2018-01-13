import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Company} from '../../company.interfaces';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-social-company-form',
  templateUrl: 'company-form.component.html',
  styleUrls: ['company-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CompanyFormComponent {

  @Input("company")
  public company: FormGroup;

  @Output("delete")
  private delete : EventEmitter<any> = new EventEmitter<any>();

  onDelete() {
    this.delete.emit();
  }

}
