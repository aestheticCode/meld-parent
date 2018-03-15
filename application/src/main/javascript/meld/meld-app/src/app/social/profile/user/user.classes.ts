import {Link} from '@aestheticcode/meld-lib';
import {BinaryFile} from '@aestheticcode/meld-lib';
import {UserForm} from "./user.interfaces";
import {Category} from '../../people/category/categories.interfaces';

export class UserFormModel implements UserForm {
  categories: Category[];
  image: BinaryFile;
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  birthday: string;
  gender : string;
  roles: string[];
  groups: string[];
  links: Link[] = [];

}
