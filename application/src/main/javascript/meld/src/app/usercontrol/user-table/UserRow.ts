import {LinksContainer} from "../../../lib/common/rest/LinksContainer";

export interface UserRow extends LinksContainer {

  id: string;

  gender : string

  email: string;

  firstName: string;

  lastName: string;

  birthday: string;

}
