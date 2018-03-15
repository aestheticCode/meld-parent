import {Directive, Input} from '@angular/core';

@Directive({
  selector: '[meldInit]'
})
export class MeldInitDirective {

  @Input()
  set meldInit(functionParameter) {}

}
