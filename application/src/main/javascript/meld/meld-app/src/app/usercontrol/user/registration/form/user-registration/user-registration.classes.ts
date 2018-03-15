import {RegistrationForm} from "./user-registration.interfaces";
import {Link} from '@aestheticcode/meld-lib';

export class RegistrationFormModel implements RegistrationForm {
  firstName: string;
  lastName: string;
  birthday: string;
  links: Link[] = [];
  password: string;
  confirm : string;
}
