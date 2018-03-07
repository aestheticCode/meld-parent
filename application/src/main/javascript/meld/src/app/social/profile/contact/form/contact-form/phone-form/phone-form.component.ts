import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Phone} from "./phone-form.interfaces";
import {TypeModel} from "./phone-form.classes";
import {FormGroup, NgForm} from '@angular/forms';

@Component({
  selector: 'app-social-phone-form',
  templateUrl: 'phone-form.component.html',
  styleUrls: ['phone-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class PhoneFormComponent {

  @Input("phone")
  public phone: FormGroup;

  public types : TypeModel[] = [];

  @Output("deleteClick")
  private deleteClick : EventEmitter<any> = new EventEmitter();

  @Output("addClick")
  private addClick : EventEmitter<any> = new EventEmitter();

  constructor() {
    this.types.push(new TypeModel("HOME", "Home"));
    this.types.push(new TypeModel("WORK", "Work"));
    this.types.push(new TypeModel("MOBILE", "Mobile"));
  }

  onDelete() {
    this.deleteClick.emit();
  }

  onAdd() {
    this.addClick.emit();
  }

}
