import {ContentChild, Directive, TemplateRef} from '@angular/core';

@Directive({
  selector: 'meld-filter'
})
export class MeldFilterDirective {

  @ContentChild(TemplateRef)
  public content : TemplateRef<any>;

  constructor() { }

}
