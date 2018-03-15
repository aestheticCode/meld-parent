import {LinksContainer} from '@aestheticcode/meld-lib';


export interface FilterTemplate {

  name: string

  firstName: string

  lastName: string

  birthdate: Date

}

export interface UserRow extends LinksContainer {

  id: string;

  gender: string

  email: string;

  firstName: string;

  lastName: string;

  birthday: string;

}
