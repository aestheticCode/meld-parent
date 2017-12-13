import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Phone} from "./phone-form.interfaces";
import {TypeModel} from "./phone-form.classes";

@Component({
  selector: 'app-social-phone-form',
  templateUrl: 'phone-form.component.html',
  styleUrls: ['phone-form.component.css']
})
export class PhoneFormComponent implements OnInit {

  @Input("phone")
  public phone: Phone;

  public types : TypeModel[] = [];

  @Input("readonly")
  public readonly : boolean = false;

  @Output("deleteClick")
  private deleteClick : EventEmitter<Phone> = new EventEmitter();

  @Output("addClick")
  private addClick : EventEmitter<any> = new EventEmitter();

  constructor() {
    this.types.push(new TypeModel("HOME", "Home"));
    this.types.push(new TypeModel("WORK", "Work"));
    this.types.push(new TypeModel("MOBILE", "Mobile"));
  }

  ngOnInit() {
  }

  onDelete() {
    this.phone.type = undefined;
    this.phone.number = undefined;
    this.deleteClick.emit(this.phone);
  }

  onAdd() {
    this.addClick.emit();
  }

}
