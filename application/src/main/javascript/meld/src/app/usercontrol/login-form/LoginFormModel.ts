import {LoginForm} from "./LoginForm";
import {Link} from "../../../lib/common/rest/Link";

export class LoginFormModel implements LoginForm {
  links: Link[] = [];
  email: string;
  password: string;
}
