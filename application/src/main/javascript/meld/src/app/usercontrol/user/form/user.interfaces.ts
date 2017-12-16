import {LinksContainer} from "../../../../lib/common/rest/LinksContainer";
import {BinaryFile} from "../../../../lib/common/rest/BinaryFile";

export interface UserForm extends LinksContainer {

  id: string;

  email: string;

  firstName: string;

  lastName: string;

  birthday: string;

  gender : string;

  image: BinaryFile;

  roles: string[];

  groups: string[];

}
