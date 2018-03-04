import {Address} from './address.interfaces';
import {LinksContainer} from '../../../../lib/common/rest/LinksContainer';
import {Category} from '../../people/category/categories.interfaces';

export interface Places extends LinksContainer {

  id : string;
  
  categories : Category[];

  addresses : Address[];

}
