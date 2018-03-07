import {LinksContainer} from '../../../../lib/common/rest/LinksContainer';
import {Phone} from './form/contact-form/phone-form/phone-form.interfaces';
import {Email} from './form/contact-form/email-form/email-form.interfaces';
import {Chat} from './form/contact-form/chat-form/chat-form.interfaces';

export interface Contact extends LinksContainer {

  id: string

  phones: Phone[]
  emails: Email[]
  chats: Chat[]

}
