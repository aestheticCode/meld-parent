import {BinaryFile} from "../../../../lib/common/rest/BinaryFile";
import {Link} from "../../../../lib/common/rest/Link";
import {UserForm} from "./user.interfaces";

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
