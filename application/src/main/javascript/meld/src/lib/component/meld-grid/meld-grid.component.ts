import {
  Component, ContentChild, ElementRef, EventEmitter, forwardRef, HostListener, Input, OnInit, Output, TemplateRef,
  ViewChild
} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {LoadWindow} from '../meld-window/LoadWindow';
import {MatCheckboxChange} from '@angular/material';
import {Items} from '../../common/search/search.interfaces';
import {QueryBuilder} from '../../common/search/search.classes';

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

  public value : any[] = [];

  @Input('multiSelect')
  public multiSelect : boolean = true;

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

  private calculateColumns() {
    let element: HTMLDivElement = this.viewPort.nativeElement;
    return Math.round(element.offsetWidth / this.columnWidth);
  }

  ngOnInit() {
    window.setTimeout(() => {
      this.columns = this.calculateColumns();
      this.refreshItems();
    }, 300);
  }

  @HostListener('window:resize')
  onDocumentResize() {
    this.columns = this.calculateColumns();
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

  @Input('itemValue')
  public itemValue = (item) => {
    return item['id'];
  };


  isItemSelected(item: any) {
    return this.value.indexOf(this.itemValue(item)) != -1;
  }

  selectItem(item: any) {
    this.value.push(this.itemValue(item));
    this.onChangeCallback(this.value);
  }

  deSelectItem(item: any) {
    let indexOf = this.value.indexOf(this.itemValue(item));
    this.value.splice(indexOf, 1);
    this.onChangeCallback(this.value);
  }

  itemSelected(item: any, checked: MatCheckboxChange) {
    if (checked.checked) {
      this.selectItem(item);
    } else {
      this.deSelectItem(item);
    }
  }

  writeValue(obj: any): void {
    if (Array.isArray(obj)) {
      this.value = obj;
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }


}
