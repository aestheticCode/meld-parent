import { Pipe, PipeTransform } from '@angular/core';
import {School} from "./school-form.interfaces";

@Pipe({
  name: 'schoolSort'
})
export class SchoolSortPipe implements PipeTransform {

  transform(value: School[], args?: any): any {
    return value.sort((lhf, rhf) => lhf.start.year - rhf.start.year )
  }

}
