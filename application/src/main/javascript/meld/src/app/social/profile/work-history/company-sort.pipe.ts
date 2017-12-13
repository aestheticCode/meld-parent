import { Pipe, PipeTransform } from '@angular/core';
import {Company} from './company.interfaces';

@Pipe({
  name: 'companySort'
})
export class CompanySortPipe implements PipeTransform {

  transform(value: Company[], args?: any): any {
    return value.sort((lhf, rhf) => Date.parse(lhf.start) - Date.parse(rhf.start) )
  }

}
