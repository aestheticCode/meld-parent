import {Address} from './address.interfaces';
import {LinksContainer} from '../../../../lib/common/rest/LinksContainer';

export interface Places extends LinksContainer {

  id : string;

  addresses : Address[]

}
