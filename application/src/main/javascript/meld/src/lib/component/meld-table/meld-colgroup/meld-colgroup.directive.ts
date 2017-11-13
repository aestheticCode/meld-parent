import { Directive, ContentChildren, QueryList } from '@angular/core';
import {MeldColDirective} from "../meld-col/meld-col.directive";

@Directive({
  selector: 'meld-colgroup'
})
export class MeldColgroupDirective {

  @ContentChildren(MeldColDirective)
  public columns: QueryList<MeldColDirective>;

  constructor() { }

}
