import { Pipe, PipeTransform } from '@angular/core';
import {FormGroup} from '@angular/forms';

@Pipe({
  name: 'meldControlValue'
})
export class MeldControlValuePipe implements PipeTransform {

  transform(value: FormGroup, args?: string[]): any {
    return value.get(args).value;
  }

}
