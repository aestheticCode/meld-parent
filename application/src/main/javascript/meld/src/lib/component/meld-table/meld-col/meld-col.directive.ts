import {Directive, Input} from "@angular/core";

@Directive({
  selector: 'meld-col'
})
export class MeldColDirective {

  @Input('width')
  public width: number = 0;

  @Input('visible')
  public visible : boolean = true;

}
