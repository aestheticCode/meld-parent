import {Contact} from "./contact-form.interfaces";
import {Phone} from "./phone-form/phone-form.interfaces";
import {Chat} from "./chat-form/chat-form.interfaces";
import {Email} from "./email-form/email-form.interfaces";

export class ContactModel implements Contact {
  id: string;
  phones: Phone[] = [];
  emails: Email[] = [];
  chats: Chat[] = [];

}
