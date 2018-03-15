import { Directive, ContentChildren, QueryList } from '@angular/core';
import {MeldTrDirective} from "../meld-tr/meld-tr.directive";

@Directive({
  selector: 'meld-tfooter'
})
export class MeldTfooterDirective {

  @ContentChildren(MeldTrDirective)
  public rows: QueryList<MeldTrDirective>;

  constructor() { }

}
