import {LinksContainer} from "../../../lib/common/rest/LinksContainer";

export interface RoleRow extends LinksContainer {

  id: string;

  name: string;

  members: string[]

}
