import { Pipe, PipeTransform } from '@angular/core';
import {School} from "./school-form.interfaces";
import {FormGroup} from '@angular/forms';

@Pipe({
  name: 'schoolSort'
})
export class SchoolSortPipe implements PipeTransform {

  transform(value: FormGroup[], args?: any): any {
    return value.sort((lhf, rhf) => lhf.get("startYear").get("year").value - rhf.get("startYear").get("year").value )
  }

}
