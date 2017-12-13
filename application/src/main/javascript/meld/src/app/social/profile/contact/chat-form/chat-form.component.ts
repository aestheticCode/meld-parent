import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Chat} from "./chat-form.interfaces";
import {TypeModel} from "./chat-form.classes";

@Component({
  selector: 'app-social-chat-form',
  templateUrl: 'chat-form.component.html',
  styleUrls: ['chat-form.component.css']
})
export class ChatFormComponent implements OnInit {

  @Input("chat")
  public chat : Chat;

  public types : TypeModel[] = [];

  @Input("readonly")
  public readonly : boolean = false;

  @Output("deleteClick")
  private deleteClick : EventEmitter<Chat> = new EventEmitter();

  @Output("addClick")
  private addClick : EventEmitter<any> = new EventEmitter();

  constructor() {
    this.types.push(new TypeModel("ICQ", "ICQ"));
    this.types.push(new TypeModel("MSN", "MSN"));
    this.types.push(new TypeModel("AIM", "AIM"));
  }

  ngOnInit() {
  }

  onDelete() {
    this.chat.type = undefined;
    this.chat.name = undefined;
    this.deleteClick.emit(this.chat);
  }

  onAdd() {
    this.addClick.emit();
  }


}
