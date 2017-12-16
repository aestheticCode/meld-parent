import {Directive, ContentChild, TemplateRef, Input} from '@angular/core';

@Directive({
  selector: 'meld-td'
})
export class MeldTdDirective {

  @ContentChild(TemplateRef)
  public content : TemplateRef<any>;

  public path : string;

  public asc : boolean;

  constructor() { }

}
