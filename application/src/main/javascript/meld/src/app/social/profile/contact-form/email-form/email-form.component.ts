import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Email} from "./email-form.interfaces";

@Component({
  selector: 'app-social-email-form',
  templateUrl: 'email-form.component.html',
  styleUrls: ['email-form.component.css']
})
export class EmailFormComponent implements OnInit {

  @Input("email")
  public email : Email;

  @Input("readonly")
  public readonly : boolean = false;

  @Output("deleteClick")
  private deleteClick : EventEmitter<Email> = new EventEmitter();

  @Output("addClick")
  private addClick : EventEmitter<any> = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  onDelete() {
    this.email.email = undefined;
    this.deleteClick.emit(this.email);
  }

  onAdd() {
    this.addClick.emit();
  }

}
