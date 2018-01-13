import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Chat} from "./chat-form.interfaces";
import {TypeModel} from "./chat-form.classes";
import {FormGroup, NgForm} from '@angular/forms';

@Component({
  selector: 'app-social-chat-form',
  templateUrl: 'chat-form.component.html',
  styleUrls: ['chat-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class ChatFormComponent {

  @Input("chat")
  public chat : FormGroup;

  public types : TypeModel[] = [];

  @Output("deleteClick")
  private deleteClick : EventEmitter<any> = new EventEmitter();

  @Output("addClick")
  private addClick : EventEmitter<any> = new EventEmitter();

  constructor() {
    this.types.push(new TypeModel("ICQ", "ICQ"));
    this.types.push(new TypeModel("MSN", "MSN"));
    this.types.push(new TypeModel("AIM", "AIM"));
  }

  onDelete() {
    this.deleteClick.emit();
  }

  onAdd() {
    this.addClick.emit();
  }


}
