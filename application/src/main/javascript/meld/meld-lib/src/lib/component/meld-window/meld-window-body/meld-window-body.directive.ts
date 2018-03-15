import {ContentChild, Directive, TemplateRef} from '@angular/core';

@Directive({
  selector: 'meld-window-body'
})
export class MeldWindowBodyDirective {

  @ContentChild(TemplateRef) template;

  constructor() { }

}
