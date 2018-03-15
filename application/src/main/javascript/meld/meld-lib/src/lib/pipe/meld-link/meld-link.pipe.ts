import { Pipe, PipeTransform } from '@angular/core';
import {Link} from "../../common/rest/Link";

@Pipe({
  name: 'meldLink'
})
export class MeldLinkPipe implements PipeTransform {

  transform(value: Link[], args?: string): any {
    if (!value) {
      return false;
    }
    let result = value.find((link:Link) => {
      return args.split(",").find((arg) => {
        return link.rel === arg
      }) !== undefined;
    });
    return result !== undefined;
  }

}
