import {LoginForm} from "./login-form.interfaces";
import {Link} from '@aestheticcode/meld-lib';

export class LoginFormModel implements LoginForm {
  firstName: string;
  lastName: string;
  birthday: string;
  links: Link[] = [];
  password: string;
}
