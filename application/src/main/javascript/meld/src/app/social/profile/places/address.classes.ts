import {Address} from "./address.interfaces";
import {Place} from '../../../../lib/component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.interfaces';

export class AddressModel implements Address {
  location: Place;
  tillNow: boolean;
  start: string;
  end: string;
}
