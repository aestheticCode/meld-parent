import { Directive, ContentChildren, QueryList } from '@angular/core';
import {MeldTrDirective} from "../meld-tr/meld-tr.directive";

@Directive({
  selector: 'meld-thead'
})
export class MeldTheadDirective {

  @ContentChildren(MeldTrDirective)
  public rows: QueryList<MeldTrDirective>;

  constructor() { }

}
