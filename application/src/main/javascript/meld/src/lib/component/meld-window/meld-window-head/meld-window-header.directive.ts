import {ContentChild, Directive, TemplateRef} from '@angular/core';

@Directive({
  selector: 'meld-window-header'
})
export class MeldWindowHeaderDirective {

  @ContentChild(TemplateRef) template;

  constructor() { }

}
