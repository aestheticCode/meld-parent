import {LinksContainer} from "../../../lib/common/rest/LinksContainer";

export interface RoleForm extends LinksContainer {

  id : string;

  name : string;

  permissions : string[]

}
