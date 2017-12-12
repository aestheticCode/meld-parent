import {Component, ContentChild, ElementRef, forwardRef, HostListener, Input, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {Items} from '../../common/query/Items';
import {LoadWindow} from '../meld-window/LoadWindow';
import {QueryBuilder} from '../../common/query/QueryBuilder';

const noop = () => {
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldGridComponent),
  multi: true
};

@Component({
  selector: 'meld-grid',
  templateUrl: 'meld-grid.component.html',
  styleUrls: ['meld-grid.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class MeldGridComponent implements OnInit, ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  @Input('items')
  public items: Items<any>;

  public itemsWindow: any[][];

  public itemsSize: number;

  @Input('rowHeight')
  public rowHeight: number;

  @Input('columnWidth')
  public columnWidth: number;

  public columns: number;

  @ViewChild('viewPort')
  private viewPort: ElementRef;

  @ContentChild(TemplateRef)
  public template: TemplateRef<any>;

  constructor() {
  }

  ngOnInit() {
    window.setTimeout(() => {
      let element: HTMLDivElement = this.viewPort.nativeElement;
      this.columns = Math.round(element.offsetWidth / this.columnWidth);
      this.refreshItems();
    }, 300);
  }

  @HostListener('window:resize')
  onDocumentResize() {
    let element: HTMLDivElement = this.viewPort.nativeElement;
    this.columns = Math.round(element.offsetWidth / this.columnWidth);
    this.refreshItems();
  }

  onWindowScroll(event: LoadWindow) {
    if (event.callback && this.items instanceof Function) {
      let query = QueryBuilder.query();
      query.index = event.loadIndex * this.columns;
      query.limit = event.loadLimit * 5 * this.columns;
      this.items(query, (data: any[], size: number) => {
        this.itemsWindow = [];
        let items = [];
        data.forEach((row, index) => {
          if (index % this.columns === 0) {
            items = [];
            this.itemsWindow.push(items);
          }
          items.push(row);
        });
        this.itemsSize = Math.round(size / this.columns);
        event.callback();
      });
    }
  }

  public refreshItems() {
    if (this.items instanceof Function) {
      let query = QueryBuilder.query();
      query.index = 0;
      query.limit = 20 * this.columns;
      this.items(query, (data: any[], size: number) => {
        this.itemsWindow = [];
        let items = [];
        data.forEach((row, index) => {
          if (index % this.columns === 0) {
            items = [];
            this.itemsWindow.push(items);
          }
          items.push(row);
        });
        this.itemsSize = Math.round(size / this.columns);
      });
    }
  }

  writeValue(obj: any): void {
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }


}
