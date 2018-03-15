import {Chat} from "./chat-form.interfaces";

export class ChatModel implements Chat {
  name: string;
  type: string;
}

export class TypeModel {

  constructor(private code: string,
              private name: string) {

  }

}
