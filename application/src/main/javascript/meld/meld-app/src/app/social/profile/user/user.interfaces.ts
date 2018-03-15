import {LinksContainer} from '@aestheticcode/meld-lib';
import {BinaryFile} from '@aestheticcode/meld-lib';
import {Category} from '../../people/category/categories.interfaces';

export interface UserForm extends LinksContainer {

  id: string;

  email: string;

  firstName: string;

  lastName: string;

  birthday: string;

  gender : string;

  image : BinaryFile;

  roles: string[];

  groups: string[];

  categories : Category[];

}
