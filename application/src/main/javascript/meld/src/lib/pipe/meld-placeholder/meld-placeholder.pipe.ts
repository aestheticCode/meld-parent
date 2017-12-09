import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'meldPlaceholder'
})
export class MeldPlaceholderPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (value) {
      return value;
    }
    return args;
  }

}
