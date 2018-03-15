import {Address} from "./address.interfaces";
import {Place} from '@aestheticcode/meld-lib';

export class AddressModel implements Address {
  id: string;
  location: Place;
  tillNow: boolean;
  start: string;
  end: string;
}
