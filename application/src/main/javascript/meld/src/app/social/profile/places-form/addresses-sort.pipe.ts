import { Pipe, PipeTransform } from '@angular/core';
import {Address} from "./address-form/address.interfaces";

@Pipe({
  name: 'addressesSort'
})
export class AddressesSortPipe implements PipeTransform {

  transform(value: Address[], args?: any): any {
    return value.sort((lhf, rhf) => Date.parse(rhf.start) - Date.parse(lhf.start))
  }

}
