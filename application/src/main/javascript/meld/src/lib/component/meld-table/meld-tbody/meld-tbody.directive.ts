import { Directive, ContentChildren, QueryList } from '@angular/core';
import {MeldTrDirective} from "../meld-tr/meld-tr.directive";

@Directive({
  selector: 'meld-tbody'
})
export class MeldTbodyDirective {

  @ContentChildren(MeldTrDirective)
  public rows: QueryList<MeldTrDirective>;

  constructor() { }

}
