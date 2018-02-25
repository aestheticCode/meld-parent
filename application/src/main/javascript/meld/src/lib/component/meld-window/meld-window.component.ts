import {
  Component, ContentChild, ElementRef, EventEmitter, HostListener, Input, OnChanges, OnInit, Output, SimpleChanges,
  ViewChild, ViewEncapsulation
} from '@angular/core';
import {Subject} from "rxjs";
import {MeldWindowHeaderDirective} from "./meld-window-head/meld-window-header.directive";
import {MeldWindowBodyDirective} from "./meld-window-body/meld-window-body.directive";
import {MeldWindowFooterDirective} from "./meld-window-footer/meld-window-footer.directive";
import {LoadWindow, ViewPort} from './meld.window.classes';
import {TouchWheelEvent} from '../../directive/meld-touch-wheel/meld-touch-wheel.classes';

@Component({
  selector: 'meld-window',
  templateUrl: 'meld-window.component.html',
  styleUrls: ['meld-window.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldWindowComponent implements OnChanges, OnInit {

  public isScrollBarVisible : boolean = false;

  @ViewChild('headerWrapper')
  private headerWrapper : ElementRef;

  @ViewChild('footerWrapper')
  private footerWrapper : ElementRef;

  @ContentChild(MeldWindowHeaderDirective)
  public header : MeldWindowHeaderDirective;

  @ContentChild(MeldWindowBodyDirective)
  public body : MeldWindowBodyDirective;

  @ContentChild(MeldWindowFooterDirective)
  public footer : MeldWindowFooterDirective;

  private debouncer: Subject<LoadWindow> = new Subject<LoadWindow>();

  private prePosition : number = 0;

  private vposition: number = 0;

  private hposition : number = 0;

  private rowsSteps: Array<number>;

  @Input()
  public rowHeight: number = 37;

  @Input()
  public rowsSize: number = 0;

  private rowIndex: number = 0;

  public marginTop : number = 0;

  public marginBottom : number = 0;

  @Output()
  private windowScrollChange: EventEmitter<LoadWindow> = new EventEmitter<LoadWindow>();

  @Output()
  private viewPortChange : EventEmitter<ViewPort> = new EventEmitter<ViewPort>();

  @ViewChild('window')
  private window: ElementRef;

  constructor() {
    this.debouncer
      .debounceTime(300)
      .subscribe((value) => {
        window.setTimeout(() => {
          this.windowScrollChange.emit(value)
        }, 1);
      });
  }

  ngOnInit(): void {
    const windowElement: HTMLElement = this.window.nativeElement;

    this.debouncer.next(new LoadWindow(0, 20, () => {

      window.setTimeout(() => {
        this.marginTop = this.computeMarginTop();
        this.marginBottom = this.computeMarginBottom();

        const windowStartIndex = Math.round(- this.marginTop / this.rowHeight);
        const windowEndIndex = windowStartIndex + Math.floor(windowElement.offsetHeight / this.rowHeight) - 1;
        this.viewPortChange.emit(new ViewPort(windowStartIndex, windowEndIndex))
      }, 300);
    }));

  }

  ngOnChanges(changes: SimpleChanges): void {
    let rowsSize = changes['rowsSize'];
    if (rowsSize) {
      this.rowsSteps = [];
      let rowStep = 0;
      for (let i = 0; i < rowsSize.currentValue; i++) {
        this.rowsSteps.push(rowStep);
        rowStep += 1 / this.rowsSize;
      }
    }
  }

  private calculatePosition(position: number): number {
/*    let positionStep: number = 0;
    for (let i = 0; i < this.rowsSteps.length; i++) {
      let rowsStep = this.rowsSteps[i];
      if (position > rowsStep) {
        positionStep = this.rowsSteps[i];
      }
    }
    if (positionStep > 1) {
      positionStep = 1;
    }
    return positionStep;*/
    return position;
  }

  vScrollBarChange(position: number) {
    this.vposition = this.calculatePosition(position);
    this.onvPositionChange();
  }

  hScrollBarChange(position: number) {
    this.hposition = position;
    this.onhPositionChange();
  }


  onTouchMove(event : TouchWheelEvent) {
    const windowElement: HTMLElement = this.window.nativeElement;
    const deltaY = event.deltaY;
    const deltaX = event.deltaX;
    const height = this.rowsSize * this.rowHeight - windowElement.clientHeight;
    this.prePosition += deltaY / height;
    this.vposition = this.calculatePosition(this.prePosition);
    this.hposition += deltaX / windowElement.offsetWidth;
    if (this.vposition < 0) {
      this.vposition = 0;
      this.prePosition = 0;
    }
    if (this.vposition > 1) {
      this.vposition = 1;
      this.prePosition = 1;
    }
    if (this.hposition < 0) {
      this.hposition = 0;
    }
    if (this.hposition > 1) {
      this.hposition = 1;
    }
    if (this.rowsSize * this.rowHeight > windowElement.offsetHeight) {
      this.onvPositionChange();
    }
    this.onhPositionChange();
    return false;

  }

  onWindowWheel(event: WheelEvent) {
    event.preventDefault();
    event.stopPropagation();
    const windowElement: HTMLElement = this.window.nativeElement;
    const deltaY = this.normalizeWheelY(event) * - 120;
    const deltaX = this.normalizeWheelX(event) * - 10;
    const height = this.rowsSize * this.rowHeight - windowElement.clientHeight;
    this.prePosition += deltaY / height;
    this.vposition = this.calculatePosition(this.prePosition);
    this.hposition += deltaX / windowElement.offsetWidth;
    if (this.vposition < 0) {
      this.vposition = 0;
      this.prePosition = 0;
    }
    if (this.vposition > 1) {
      this.prePosition = 1;
      this.vposition = 1;
    }
    if (this.hposition < 0) {
      this.hposition = 0;
    }
    if (this.hposition > 1) {
      this.hposition = 1;
    }
    if (this.rowsSize * this.rowHeight > windowElement.offsetHeight) {
      this.onvPositionChange();
    }
    this.onhPositionChange();
    return false;
  }

  normalizeWheelY(event : WheelEvent) {
    let wheelDelta = event.wheelDeltaY;
    let detail = event.detail;
    if (detail) {
      if (wheelDelta) {
        return wheelDelta / detail / 40 * detail > 0 ? 1 : -1; // Opera
      }
      else {
        return -detail / 3; // Firefox;         TODO: do not /3 for OS X
      }
    } else {
      return wheelDelta / 120; // IE/Safari/Chrome TODO: /3 for Chrome OS X
    }
  }

  normalizeWheelX(event : WheelEvent) {
    let wheelDelta = event.wheelDeltaX;
    let detail = event.detail;
    if (detail) {
      if (wheelDelta) {
        return wheelDelta / detail / 40 * detail > 0 ? 1 : -1; // Opera
      }
      else {
        return -detail / 3; // Firefox;         TODO: do not /3 for OS X
      }
    } else {
      return wheelDelta / 120; // IE/Safari/Chrome TODO: /3 for Chrome OS X
    }
  }

  onhPositionChange() {
    const windowElement: HTMLElement = this.window.nativeElement;
    const windowContentElement: HTMLElement = windowElement.children[0] as HTMLElement;

    const left = (windowContentElement.offsetWidth - windowElement.offsetWidth - 10) * this.hposition;

    windowElement.scrollLeft = left;

    if (this.headerWrapper) {
      this.headerWrapper.nativeElement.scrollLeft = left;
    }

    if (this.footerWrapper) {
      this.footerWrapper.nativeElement.scrollLeft = left;
    }
  }

  onvPositionChange() {
    const windowElement: HTMLElement = this.window.nativeElement;
    const windowContentElement: HTMLElement = windowElement.children[0] as HTMLElement;

    this.marginTop = this.computeMarginTop();
    this.marginBottom = this.computeMarginBottom();

    windowContentElement.style.marginTop = this.marginTop + 'px';
    windowContentElement.style.marginBottom = 10000 + 'px';

    let higherRowIndex = this.computeHigherRowIndex(this.rowIndex, this.rowsToLoad());
    let lowerRowIndex = this.computeLowerRowIndex(this.rowIndex, this.rowsToLoad());

    if (this.marginTop > 0) {
      this.debouncer.next(new LoadWindow(higherRowIndex, this.rowsToLoad(), () => {
        this.rowIndex = higherRowIndex;
        this.onvPositionChange();
      }));
    } else if (this.marginBottom < windowElement.offsetHeight && this.rowsToLoad() > 0) {
      this.debouncer.next(new LoadWindow(lowerRowIndex, this.rowsToLoad(), () => {
        this.rowIndex = lowerRowIndex;
        this.onvPositionChange();
      }));
    }

    const windowStartIndex = Math.round(- this.marginTop / this.rowHeight);
    const windowEndIndex = windowStartIndex + Math.round(windowElement.offsetHeight / this.rowHeight);
    this.viewPortChange.emit(new ViewPort(windowStartIndex, windowEndIndex))
  };

  private computeMarginTop(): number {
    return -this.vposition * ( this.rowsSize * this.rowHeight - this.window.nativeElement.offsetHeight ) + this.rowIndex * this.rowHeight;
  }

  private computeMarginBottom() {
    return this.marginTop + this.rowsToLoad() * 5 * this.rowHeight;
  }

  private endIndex() {
    if (this.rowsSize < this.rowsToLoad()) {
      return this.rowsSize;
    }
    return this.startIndex() + this.rowsToLoad();
  }

  private startIndex() {
    return Math.round(- this.marginTop / this.rowHeight);
  }

  computeLowerRowIndex(rowIndex, rowsToLoad): number {
    let index;
    index = rowIndex + rowsToLoad * 3;
    if (index < 0) {
      index = 0;
    }
    return Math.round(index) || 0;
  }

  computeHigherRowIndex(rowIndex, rowsToLoad): number {
    let index;
    index = rowIndex - rowsToLoad * 3;
    if (index < 0) {
      index = 0;
    }
    return Math.round(index) || 0;
  }

  rowsToLoad(): number {
    return Math.round(this.window.nativeElement.offsetHeight / this.rowHeight);
  }

  scrollBarsVisible(event : MouseEvent) {
    event.stopPropagation();

    this.isScrollBarVisible = true;

    return false;
  }

  @HostListener('window:mouseover')
  onDocumentMouseMove() {
    this.isScrollBarVisible = false;
  }

  calculateWindow() {
    let rowsCount = Math.round(this.window.nativeElement.offsetHeight / this.rowHeight);
    let begin = Math.round(this.vposition * (this.rowsSize - rowsCount));
    return begin + ' - ' + (begin + rowsCount);
  }

}
