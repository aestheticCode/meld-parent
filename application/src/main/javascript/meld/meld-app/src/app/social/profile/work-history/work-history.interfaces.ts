import {Company} from './company.interfaces';
import {LinksContainer} from '@aestheticcode/meld-lib';
import {Category} from '../../people/category/categories.interfaces';

export interface WorkHistory extends LinksContainer {

  id : string

  categories : Category;

  companies : Company[]

}
