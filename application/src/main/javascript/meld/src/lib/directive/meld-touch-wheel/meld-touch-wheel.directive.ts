import {Directive, ElementRef, EventEmitter, Input, Output} from '@angular/core';
import {TouchWheelEvent} from './meld-touch-wheel.classes';

@Directive({
  selector: '[meldTouchWheel]'
})
export class MeldTouchWheelDirective {

  startX : number = 0;
  startY : number = 0;

  @Output('meldTouchWheelChange')
  private meldTouchWheelChange : EventEmitter<TouchWheelEvent> = new EventEmitter<TouchWheelEvent>();

  constructor(private elementRef : ElementRef) {

    let element  : HTMLElement = elementRef.nativeElement;

    element.addEventListener('touchstart', (event : TouchEvent) => {
      this.startX = event.changedTouches.item(0).clientX;
      this.startY = event.changedTouches.item(0).clientY;
    });

    element.addEventListener('touchmove', (event : TouchEvent) => {
      event.preventDefault();
      event.stopPropagation();
      let item = event.changedTouches.item(0);
      this.meldTouchWheelChange.emit(new TouchWheelEvent(this.startX - item.clientX, this.startY - item.clientY));
      this.startX = item.clientX;
      this.startY = item.clientY;
      return false;
    });

  }

}
