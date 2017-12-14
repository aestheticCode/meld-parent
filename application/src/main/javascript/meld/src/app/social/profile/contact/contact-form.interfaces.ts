import {Phone} from './contact-form/phone-form/phone-form.interfaces';
import {Email} from './contact-form/email-form/email-form.interfaces';
import {Chat} from './contact-form/chat-form/chat-form.interfaces';

export interface Contact {

  id: string

  phones: Phone[]
  emails: Email[]
  chats: Chat[]

}
