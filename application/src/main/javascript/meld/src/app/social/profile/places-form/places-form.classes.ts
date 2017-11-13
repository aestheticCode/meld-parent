import {Places} from "./places-form.interfaces";
import {Address} from "./address-form/address-form.interfaces";

export class PlacesModel implements Places {
  addresses: Address[] = [];
}
