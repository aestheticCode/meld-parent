import {Company} from './company.interfaces';
import {LinksContainer} from '../../../../lib/common/rest/LinksContainer';

export interface WorkHistory extends LinksContainer {

  id : string
  companies : Company[]

}
