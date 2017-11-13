import {Address} from "./address-form.interfaces";

export class AddressModel implements Address {
  street: string;
  zipCode: string;
  state: string;
  city: string;
  country: string;
  start: string;
  end: string;
}
