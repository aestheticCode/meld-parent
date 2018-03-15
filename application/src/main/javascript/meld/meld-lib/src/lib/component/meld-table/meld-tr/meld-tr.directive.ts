import { Directive, ContentChildren, QueryList } from '@angular/core';
import {MeldTdDirective} from "../meld-td/meld-td.directive";

@Directive({
  selector: 'meld-tr'
})
export class MeldTrDirective {

  @ContentChildren(MeldTdDirective)
  public columns: QueryList<MeldTdDirective>;

  constructor() {
  }

}
