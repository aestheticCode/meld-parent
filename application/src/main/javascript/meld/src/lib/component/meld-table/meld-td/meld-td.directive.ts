import { Directive, ContentChild, TemplateRef } from '@angular/core';

@Directive({
  selector: 'meld-td'
})
export class MeldTdDirective {

  @ContentChild(TemplateRef)
  public content : TemplateRef<any>;

  constructor() { }

}
