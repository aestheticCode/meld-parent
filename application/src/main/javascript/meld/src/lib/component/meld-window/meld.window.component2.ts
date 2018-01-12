import {Component, ViewChild, Input, Output, EventEmitter, ElementRef, OnChanges, SimpleChanges} from "@angular/core";
import {GpmWindowRowIndex} from "component/gpm-window/gpm-window.row.index";
import {GpmWindowViewPort} from "component/gpm-window/gpm-window.viewport";
import {GpmDimensions} from "component/gpm-dimensions/gpm-dimensions.dimensions";
import {Subject} from "rxjs";

@Component({
  selector: 'gpm-window',
  templateUrl: 'gpm-window.component.html',
  styleUrls: ['gpm-window.component.css'],
  host: {
    'style': 'display: flex;'
  }
})
export class GpmWindowComponent implements OnChanges {

  public debouncer: Subject<GpmWindowRowIndex> = new Subject<GpmWindowRowIndex>();

  public showScrollBar: boolean = true;

  public _position: number = 0;

  public prePosition: number = 0;

  @Output()
  public positionChange: EventEmitter<number> = new EventEmitter<number>();

  @Input()
  public rowHeight: number = 37;

  @Input()
  public rowsSize: number = 0;

  public rowsSteps: Array<number>;

  public rowIndex: number = 0;

  @Output()
  public rowIndexChange: EventEmitter<GpmWindowRowIndex> = new EventEmitter<GpmWindowRowIndex>();

  @Input()
  public rowsToLoad: number = 75;

  @ViewChild('window')
  public window: ElementRef;

  private windowHeight: number = 0;

  @ViewChild('windowContent')
  public windowContent: ElementRef;

  @Output()
  public windowViewPortChange: EventEmitter<GpmWindowViewPort> = new EventEmitter<GpmWindowViewPort>();

  constructor() {
    this.debouncer
      .debounceTime(300)
      .subscribe((value) => this.rowIndexChange.emit(value));
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

  @Input()
  public set position(value: number) {
    this._position = value;
    this.onPositionChange();
    this.positionChange.emit(value);
  }

  public get position() {
    return this._position;
  }

  public onWindowDimensionsChange(event: GpmDimensions) {
    if (event.offsetHeight > 0 && event.hasOffsetHeightChanged) {
      this.windowHeight = event.offsetHeight;
    }
  }

  public onWindowContentDimensionsChange(event: GpmDimensions) {
    this.showScrollBar = event.offsetHeight > this.windowHeight;
    this.onPositionChange();
  }

  private scrollBarChange(position: number) {
    this.position = this.calculatePosition(position);
    this.onPositionChange();
  }

  public onWindowWheel(event: WheelEvent) {
    event.stopPropagation();
    const windowElement: HTMLElement = this.window.nativeElement;
    const deltaY = - this.wheelDistance(event) * this.rowHeight;
    const height = this.rowsSize * this.rowHeight - windowElement.clientHeight;
    this.prePosition += (deltaY / height);
    this.position = this.calculatePosition(this.prePosition);
    this.onPositionChange();
    return false;
  }

  private wheelDistance(event: WheelEvent) : number {
    let wheelDelta = event.wheelDelta;
    let detail = event.deltaY;
    if (detail) {
      if (wheelDelta) {
        return wheelDelta / detail / 40 * detail > 0 ? 1 : -1;  // Opera
      }
      else {
        return - detail / 3;                                    // Firefox
      }
    }
    else {
      return wheelDelta / 120;                                  // IE/Safari/Chrome
    }
  };

  private calculatePosition(position: number): number {
    let positionStep: number = 0;
    for (let i = 0; i < this.rowsSteps.length; i++) {
      let rowsStep = this.rowsSteps[i];
      if (position > rowsStep) {
        positionStep = this.rowsSteps[i];
      }
    }
    if (positionStep > 1) {
      positionStep = 1;
    }
    return positionStep;
  }

  private onPositionChange() {
    const windowContentElement: HTMLElement = this.windowContent.nativeElement;
    const windowElement: HTMLElement = this.window.nativeElement;
    if (windowContentElement.offsetHeight == 0 || windowElement.offsetHeight == 0) {
      return;
    }

    const marginTopOriginal = this.computeMarginTopOriginal(this.position, this.rowsSize);
    const compensation = this.computeRowCompensation(this.rowIndex);
    let marginTop = marginTopOriginal + compensation;
    windowContentElement.style.marginTop = marginTop + 'px';
    windowContentElement.style.marginBottom = -marginTop + 'px';

    const lowerGap = marginTop + windowContentElement.offsetHeight - windowElement.offsetHeight;
    if (lowerGap < 0) {
      let rowIndex = this.computeLowerRowIndex(marginTopOriginal, this.computeRowsToLoad());
      this.debouncer.next(new GpmWindowRowIndex(rowIndex, this.computeRowsToLoad() * 3, () => {
        this.rowIndex = rowIndex;
        this.onPositionChange();
      }));
    }
    if (marginTop > 0) {
      let rowIndex = this.computeHigherRowIndex(marginTopOriginal, this.computeRowsToLoad());
      this.debouncer.next(new GpmWindowRowIndex(rowIndex, this.computeRowsToLoad() * 3, () => {
        this.rowIndex = rowIndex;
        this.onPositionChange();
      }));
    }
    const windowStartIndex = -Math.round(marginTop / this.rowHeight);
    const windowRowCount = Math.round(windowElement.offsetHeight / this.rowHeight);
    this.windowViewPortChange.emit(new GpmWindowViewPort(windowStartIndex, windowRowCount, this.showScrollBar));
  };

  private computeMarginTopOriginal(position, size): number {
    return Math.round(-position * ( size * this.rowHeight ));
  }

  private computeLowerRowIndex(marginTop, rowsToLoad): number {
    let index;
    index = Math.round(-marginTop / this.rowHeight);
    index -= rowsToLoad;
    if (index < 0) {
      index = 0;
    }
    return index || 0;
  }

  private computeHigherRowIndex(marginTop, rowsToLoad): number {
    let index;
    index = Math.round(-marginTop / this.rowHeight);
    index -= rowsToLoad * 2;
    if (index < 0) {
      index = 0;
    }
    return index || 0;
  }

  private computeRowCompensation(rowIndex): number {
    return rowIndex * this.rowHeight;
  }

  private computeRowsToLoad(): number {
    return Math.round(this.rowsToLoad / 3);
  }

}
