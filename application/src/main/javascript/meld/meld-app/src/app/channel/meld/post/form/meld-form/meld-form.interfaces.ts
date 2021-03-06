import {LinksContainer} from '@aestheticcode/meld-lib';
import {Category} from '../../../../../social/people/category/categories.interfaces';


export interface MeldPost extends LinksContainer {
  id: string
  text: string
  type: string
  category : Category
}
