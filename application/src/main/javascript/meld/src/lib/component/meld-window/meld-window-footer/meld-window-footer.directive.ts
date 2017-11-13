import {ContentChild, Directive, TemplateRef} from '@angular/core';

@Directive({
  selector: 'meld-window-footer'
})
export class MeldWindowFooterDirective {

  @ContentChild(TemplateRef) template;

  constructor() { }

}
