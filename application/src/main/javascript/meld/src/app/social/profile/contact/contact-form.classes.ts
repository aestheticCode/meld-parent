import {Contact} from "./contact-form.interfaces";
import {Phone} from './contact-form/phone-form/phone-form.interfaces';
import {Email} from './contact-form/email-form/email-form.interfaces';
import {Chat} from './contact-form/chat-form/chat-form.interfaces';
import {Link} from '../../../../lib/common/rest/Link';

export class ContactModel implements Contact {
  links: Link[];
  id: string;
  phones: Phone[] = [];
  emails: Email[] = [];
  chats: Chat[] = [];

}
