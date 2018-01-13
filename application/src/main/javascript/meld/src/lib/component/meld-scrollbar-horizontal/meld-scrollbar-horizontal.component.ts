import {
  Component, Input, Output, HostListener, ViewChild, EventEmitter, OnChanges, SimpleChanges, ElementRef,
  ViewEncapsulation
} from '@angular/core';

@Component({
  selector: 'meld-scrollbar-horizontal',
  templateUrl: 'meld-scrollbar-horizontal.component.html',
  styleUrls: ['meld-scrollbar-horizontal.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldScrollbarHorizontalComponent implements OnChanges {

  start : number;

  @Input() position : number;

  @Output() positionChange : EventEmitter<number> = new EventEmitter<number>();

  @ViewChild('scrollBar') scrollBar : ElementRef;

  @ViewChild('cursor') cursor : ElementRef;

  cursorMouseDown(event : MouseEvent) {
    this.start = event.screenX;
  }

  @HostListener('document:mousemove', ['$event'])
  documentMouseMove(event : MouseEvent) {
    if (this.start) {
      const delta = event.screenX - this.start;
      const cursorElement: HTMLElement = this.cursor.nativeElement;
      const scrollBarElement: HTMLElement = this.scrollBar.nativeElement;
      const oldCursorPosition = cursorElement.offsetLeft;
      const scrollBarWidth = scrollBarElement.offsetWidth;
      let newCursorPosition = oldCursorPosition + delta;
      if (newCursorPosition < 0) {
        newCursorPosition = 0;
      }
      if (newCursorPosition > scrollBarWidth - 16) {
        newCursorPosition = scrollBarWidth - 16;
      }
      cursorElement.style.left = newCursorPosition + 'px';
      if (newCursorPosition >= 0 && newCursorPosition <= scrollBarWidth - 16) {
        this.start = event.screenX;
        this.positionChange.emit(newCursorPosition / (scrollBarWidth - 16));
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
    const change = changes['position'];
    if (change) {
      const cursorElement: HTMLElement = this.cursor.nativeElement;
      const scrollBarElement: HTMLElement = this.scrollBar.nativeElement;
      cursorElement.style.left = this.position * (scrollBarElement.offsetWidth - 16) + 'px';
    }
  }

}
