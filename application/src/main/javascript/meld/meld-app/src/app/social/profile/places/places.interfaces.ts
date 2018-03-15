import {Address} from './address.interfaces';
import {LinksContainer} from '@aestheticcode/meld-lib';
import {Category} from '../../people/category/categories.interfaces';

export interface Places extends LinksContainer {

  id : string;
  
  categories : Category[];

  addresses : Address[];

}
