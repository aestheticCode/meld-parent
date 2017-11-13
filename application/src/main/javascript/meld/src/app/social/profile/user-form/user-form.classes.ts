import {Link} from "../../../../lib/common/rest/Link";
import {BinaryFile} from "../../../../lib/common/rest/BinaryFile";
import {UserForm} from "./user-form.interfaces";

export class UserFormModel implements UserForm {
  image: BinaryFile;
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  birthday: string;
  roles: string[];
  groups: string[];
  links: Link[] = [];

}
