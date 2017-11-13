import { Pipe, PipeTransform } from '@angular/core';
import {TableColumn} from "./TableColumn";

@Pipe({
  name: 'meldTableSorter',
  pure : false
})
export class MeldTableSorterPipe implements PipeTransform {

  transform(value: any, columnConfiguration: TableColumn[], stayVisible : boolean = false): any {

    const array = Array.from(value);
    const result = [];

    columnConfiguration.forEach((configuration : TableColumn) => {
      if (configuration.visible || stayVisible) {
        result.push(array[configuration.index]);
      }
    });

    return result;
  }

}
