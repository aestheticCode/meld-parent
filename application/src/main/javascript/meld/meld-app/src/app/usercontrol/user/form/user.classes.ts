import {BinaryFile} from '@aestheticcode/meld-lib';
import {Link} from '@aestheticcode/meld-lib';
import {UserForm} from "./user.interfaces";

export class UserFormModel implements UserForm {
  password: string;
  gender: string;
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
