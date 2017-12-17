import { Pipe, PipeTransform } from '@angular/core';
import {Enum} from './meld-enum.interfaces';

@Pipe({
  name: 'meldEnum'
})
export class MeldEnumPipe implements PipeTransform {

  transform(value: string, args?: Enum[]): any {
    return args
      .filter((enumarable) => enumarable.value === value)
      .shift()
      .label;
  }

}
