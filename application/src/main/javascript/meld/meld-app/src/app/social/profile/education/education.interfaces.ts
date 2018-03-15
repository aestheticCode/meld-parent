import {School} from "./school-form.interfaces";
import {LinksContainer} from '@aestheticcode/meld-lib';
import {Category} from '../../people/category/categories.interfaces';

export interface Education extends LinksContainer {

  id : string

  schools : School[]

  categories : Category[]

}
