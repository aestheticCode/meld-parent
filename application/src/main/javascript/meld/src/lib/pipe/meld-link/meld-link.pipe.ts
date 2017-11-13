import { Pipe, PipeTransform } from '@angular/core';
import {Link} from "../../common/rest/Link";

@Pipe({
  name: 'meldLink'
})
export class MeldLinkPipe implements PipeTransform {

  transform(value: Link[], args?: any): any {
    let result = value.find((link:Link) => {
      return link.rel === args
    });
    return result !== undefined;
  }

}
