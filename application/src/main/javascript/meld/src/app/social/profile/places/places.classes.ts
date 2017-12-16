import {Places} from "./places.interfaces";
import {Address} from './address.interfaces';
import {Link} from '../../../../lib/common/rest/Link';

export class PlacesModel implements Places {
  links: Link[] = [];
  id: string;
  addresses: Address[] = [];
}
