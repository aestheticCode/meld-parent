import {LinksContainer} from "../../../lib/common/rest/LinksContainer";

export interface GroupForm extends LinksContainer {

  id: string;

  name: string;

  roles: string[];

}
