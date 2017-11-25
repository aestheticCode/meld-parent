import {LinksContainer} from "../../../lib/common/rest/LinksContainer";

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
