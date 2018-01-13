import {
  Component, Input, Output, HostListener, ViewChild, EventEmitter, OnChanges, SimpleChanges, ElementRef,
  ViewEncapsulation
} from '@angular/core';

@Component({
  selector: 'meld-scrollbar-vertical',
  templateUrl: 'meld-scrollbar-vertical.component.html',
  styleUrls: ['meld-scrollbar-vertical.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldScrollbarVerticalComponent implements OnChanges {

  start : number;

  @Input() position : number;

  @Output() positionChange : EventEmitter<number> = new EventEmitter<number>();

  @ViewChild('scrollBar') scrollBar : ElementRef;

  @ViewChild('cursor') cursor : ElementRef;

  cursorMouseDown(event : MouseEvent) {
    this.start = event.screenY;
  }

  @HostListener('document:mousemove', ['$event'])
  documentMouseMove(event : MouseEvent) {
    if (this.start) {
      var delta = event.screenY - this.start;
      var cursorElement : HTMLElement = this.cursor.nativeElement;
      var scrollBarElement : HTMLElement = this.scrollBar.nativeElement;
      var oldCursorPosition = cursorElement.offsetTop;
      var scrollBarHeight = scrollBarElement.offsetHeight;
      var newCursorPosition = oldCursorPosition + delta;
      if (newCursorPosition < 0) {
        newCursorPosition = 0;
      }
      if (newCursorPosition > scrollBarHeight - 16) {
        newCursorPosition = scrollBarHeight - 16;
      }
      cursorElement.style.top = newCursorPosition + 'px';
      if (newCursorPosition >= 0 && newCursorPosition <= scrollBarHeight - 16) {
        this.start = event.screenY;
        this.positionChange.emit(newCursorPosition / (scrollBarHeight - 16));
      }
    }
  }

  @HostListener('document:selectstart')
  documentNoSelect() {
    if (this.start) {
      return false;
    }
    return true;
  }

  @HostListener('document:mouseup')
  documentMouseUp() {
    if (this.start) {
      this.start = null;
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    var change = changes['position'];
    if (change) {
      var cursorElement : HTMLElement = this.cursor.nativeElement;
      var scrollBarElement : HTMLElement = this.scrollBar.nativeElement;
      cursorElement.style.top = this.position * (scrollBarElement.offsetHeight - 16) + 'px';
    }
  }

}
