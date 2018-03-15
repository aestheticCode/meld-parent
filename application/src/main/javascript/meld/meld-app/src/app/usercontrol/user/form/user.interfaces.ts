import {LinksContainer} from '@aestheticcode/meld-lib';
import {BinaryFile} from '@aestheticcode/meld-lib';

export interface UserForm extends LinksContainer {

  id: string;

  email: string;

  firstName: string;

  lastName: string;

  birthday: string;

  gender : string;

  password : string;

  image: BinaryFile;

  roles: string[];

  groups: string[];

}
