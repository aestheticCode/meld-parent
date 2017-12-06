import {RegistrationForm} from "./user-registration.interfaces";
import {Link} from "../../../lib/common/rest/Link";

export class RegistrationFormModel implements RegistrationForm {
  firstName: string;
  lastName: string;
  birthday: string;
  links: Link[] = [];
  password: string;
  confirm : string;
}
