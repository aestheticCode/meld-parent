import {LinksContainer} from '../../../../../lib/common/rest/LinksContainer';

export interface GroupRow extends LinksContainer {

  id: string;

  name: string;

  roles: string[];

}
