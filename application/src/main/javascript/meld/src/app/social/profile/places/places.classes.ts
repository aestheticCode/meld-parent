import {Places} from "./places.interfaces";
import {Address} from './address.interfaces';

export class PlacesModel implements Places {
  id: string;
  addresses: Address[] = [];
}
