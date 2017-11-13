import { Directive, ElementRef, HostListener, DoCheck, Output, EventEmitter } from '@angular/core';
import {Dimensions} from "./Dimensions";

@Directive({
  selector: '[meldDimensions]'
})
export class MeldDimensionsDirective implements DoCheck {

  private offsetHeight : number = 0;

  private offsetWidth : number = 0;

  @Output() dimensionsChange : EventEmitter<Dimensions> = new EventEmitter<Dimensions>();

  constructor(private elementRef : ElementRef) { }

  @HostListener('document:resize')
  onDocumentResize() {
    this.processDimensions();
  }

  ngDoCheck(): void {
    this.processDimensions();
  }

  processDimensions() {
    var element : HTMLElement = this.elementRef.nativeElement;

    if (this.offsetHeight != element.offsetHeight) {
      this.dimensionsChange.emit(new Dimensions(
        this.offsetHeight,
        element.offsetHeight,
        this.offsetWidth,
        element.offsetWidth
      ));
      this.offsetHeight = element.offsetHeight;
    }

    if (this.offsetWidth != element.offsetWidth) {
      this.dimensionsChange.emit(new Dimensions(
        this.offsetHeight,
        element.offsetHeight,
        this.offsetWidth,
        element.offsetWidth
      ));
      this.offsetWidth = element.offsetWidth;
    }

  }
}
