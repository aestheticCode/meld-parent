import {
  Component, ContentChild, ElementRef, EventEmitter, forwardRef, HostListener, Input, OnChanges, Output, SimpleChanges, TemplateRef,
  ViewChild
} from '@angular/core';
import {ControlValueAccessor, FormControl, NG_VALIDATORS, NG_VALUE_ACCESSOR} from '@angular/forms';
import {MeldTableComponent} from '../meld-table/meld-table.component';
import {Subject} from 'rxjs/Subject';
import {Items} from '../../common/search/search.interfaces';
import {Search, Selects} from './meld-combobox.interfaces';

const noop = () => {
};

export const CUSTOM_INPUT_NG_VALIDATORS: any = {
  provide: NG_VALIDATORS,
  useExisting: forwardRef(() => MeldComboBoxComponent),
  multi: true
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldComboBoxComponent),
  multi: true
};

@Component({
  selector: 'meld-combobox',
  templateUrl: 'meld-combobox.component.html',
  styleUrls: ['meld-combobox.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR, CUSTOM_INPUT_NG_VALIDATORS]
})
export class MeldComboBoxComponent implements OnChanges, ControlValueAccessor {

  public value: any;

  public size: number = 0;

  public showInfo: boolean = false;

  public showOverlay: boolean = false;

  @Input()
  public required: any = false;

  @Input('filter')
  public filter: string = '';

  @Output("filterChange")
  public filterChange : EventEmitter<string> = new EventEmitter<string>();

  @Input('placeholder')
  public placeholder: string = '';

  @Input('items')
  public items: Selects<any>;

  @Input('readonly')
  public readonly: boolean = false;

  @Input('disabled')
  public disabled: boolean = false;

  @Output('selectItemChange')
  private selectItemChange: EventEmitter<any> = new EventEmitter<any>();

  @ContentChild(TemplateRef)
  public template: TemplateRef<any>;

  @ViewChild('table')
  public table: MeldTableComponent;

  @Input('rowHeight')
  public rowHeight: number = 28;

  private filterChanges = new Subject<string>();

  private onTouchedCallback: () => void = noop;

  private onChangeCallback: (value: any) => void = noop;

  constructor(private elRef: ElementRef) {
    this.filterChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .subscribe((event: string) => {
        this.showOverlay = true;
        this.filter = event;
        this.filterChange.emit(event);
        this.table.refreshItems();
        this.value = null;
      });
  }

  validate(control: FormControl) {
    if (this.required || this.required === "") {
      if (control.pristine) {
        return null;
      }

      if (control.value && control.value.length > 0) {
        return null;
      } else {
        return {required: true}
      }
    }
  }

  public parentItems: Items<any> = (query, callback) => {
    const search: Search = {
      filter: this.filter,
      index: query.index,
      limit: query.limit,
    };
    this.items(search, (data: [any], size: number) => {
      callback(data, size);
      this.size = size;
    });
  };

  @Input('itemValue')
  public itemValue = (item) => {
    if (item == null) {
      return null;
    }
    return item['id'];
  };

  @Input('itemName')
  public itemName = (item) => {
    if (item == null) {
      return null;
    }
    return item['name'];
  };

  ngOnChanges(changes: SimpleChanges): void {
    if (this.table && changes['itemValuePath']) {
      this.table.itemValue = this.itemValue;
    }
  }

  public onFilterChange(event) {
    this.filterChanges.next(event);
  }

  public onSelectItemChange(event) {
    this.value = this.itemValue(event);
    this.filter = this.itemName(event);
    this.filterChange.emit(this.filter)
    this.onChangeCallback(this.value);
    this.showOverlay = false;
    this.selectItemChange.emit(event);
  }

  clearValue() {
    this.filter = '';
    this.filterChange.emit('');
    this.value = null;
    this.onChangeCallback(null);
    this.onSelectItemChange(null);
  }

  onShowOverlay() {
    window.setTimeout(() => {
      if (this.disabled || this.readonly) {
        this.showOverlay = false;
      } else {
        this.showOverlay = true;
        this.table.refreshItems();
      }
    }, 300);
  }

  onHideOverlay() {
    this.showOverlay = false;
  }

  @HostListener('document:click')
  private onDocumentClick() {
    this.showOverlay = false;
  }

  onKeyUp(event: KeyboardEvent) {

    switch (event.keyCode) {
      case 13 : {
        let item = this.table.itemsWindow[this.table.hoveredIndex];
        let itemValue = this.itemValue(item);
        let itemName = this.itemName(item);
        this.value = itemValue;
        this.filter = itemName;
        this.filterChange.emit(itemName);
        this.onChangeCallback(this.value);
        this.showOverlay = false;
        this.selectItemChange.emit(item);
      }
        break;
    }

    this.table.onKeyUp(event);
  }

  writeValue(obj: any): void {
    if (obj) {
      this.value = obj;

      let itemName = this.itemName(this.value);
      if (itemName) {
        this.filter = itemName;
        this.filterChange.emit(itemName);
      } else {
        const search: Search = {
          selected: obj
        };

        const callback = (data: [any], size: number) => {
          if (this.filter.length === 0) {
            this.filter = this.itemName(data[0]);
            this.filterChange.emit(this.filter);
          }
        };
        this.items(search, callback);
      }

    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = (value: any) => {
      fn(value);
    };
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

}
