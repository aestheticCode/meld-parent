import {Places} from "./places.interfaces";
import {Address} from './address.interfaces';
import {Link} from '@aestheticcode/meld-lib';
import {Category} from '../../people/category/categories.interfaces';

export class PlacesModel implements Places {
  categories: Category[] = [];
  links: Link[] = [];
  id: string;
  addresses: Address[] = [];
}
