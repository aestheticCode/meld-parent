import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Email} from "./email-form.interfaces";
import {FormGroup, NgForm} from '@angular/forms';

@Component({
  selector: 'app-social-email-form',
  templateUrl: 'email-form.component.html',
  styleUrls: ['email-form.component.css']
})
export class EmailFormComponent {

  @Input("email")
  public email : FormGroup;

  @Output("deleteClick")
  private deleteClick : EventEmitter<any> = new EventEmitter();

  @Output("addClick")
  private addClick : EventEmitter<any> = new EventEmitter();

  onDelete() {
    this.deleteClick.emit();
  }

  onAdd() {
    this.addClick.emit();
  }

}
