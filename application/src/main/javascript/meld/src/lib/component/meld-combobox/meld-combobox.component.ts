import {
  Component, ContentChild, ElementRef, EventEmitter, forwardRef, HostBinding, HostListener, Input, OnChanges, OnInit, Optional, Output,
  Self,
  SimpleChanges, TemplateRef, ViewChild
} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR, NgControl, NgModel} from '@angular/forms';
import {MeldTableComponent} from '../meld-table/meld-table.component';
import {MatFormFieldControl} from '@angular/material';
import {Subject} from 'rxjs/Subject';
import {FocusMonitor} from '@angular/cdk/a11y';
import {Objects} from '../../common/utils/Objects';
import {QueryBuilder} from '../../common/search/search.classes';
import {Items} from '../../common/search/search.interfaces';
import {Search, Selects} from './meld-combobox.interfaces';

const noop = () => {
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
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR,
    {provide: MatFormFieldControl, useExisting: MeldComboBoxComponent}]
})
export class MeldComboBoxComponent implements OnChanges, ControlValueAccessor, MatFormFieldControl<any> {

  static nextId = 0;

  stateChanges = new Subject<void>();

  @HostBinding()
  id = `meld-combobox-${MeldComboBoxComponent.nextId++}`;

  @HostBinding('attr.aria-describedby')
  describedBy = '';

  @Input('required')
  required: boolean;

  errorState: boolean;

  @Optional() @Self() public ngControl: NgControl;

  get focused() {
     return document.activeElement === this.input.nativeElement;
  }

  get shouldPlaceholderFloat() {
    return this.focused || ! this.empty;
  }

  setDescribedByIds(ids: string[]): void {
    this.describedBy = ids.join(' ');
  }

  onContainerClick(event: MouseEvent): void {
    if ((event.target as Element).tagName.toLowerCase() != 'input') {
      this.elRef.nativeElement.querySelector('input').focus();
    }
  }

  get empty() {
    return Objects.isNull(this.value);
  }


  public value: any;

  public size: number = 0;

  public showInfo: boolean = false;

  public showOverlay: boolean = false;

  @Input('filter')
  public filter: string = '';

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

  @ViewChild('input')
  private input : ElementRef;

  @Input('rowHeight')
  public rowHeight : number = 28;

  private filterChanges = new Subject<string>();

  private onTouchedCallback: () => void = noop;

  private onChangeCallback: (value: any) => void = noop;

  constructor(private elRef: ElementRef) {
    this.filterChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .subscribe((event : string) => {
        this.showOverlay = true;
        this.filter = event;
        this.table.refreshItems();
        this.value = null;
      })
  }


  public parentItems: Items<any> = (query, callback) => {
    const search : Search = {
      filter : this.filter,
      index : query.index,
      limit : query.limit,
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
    this.onChangeCallback(this.value);
    this.showOverlay = false;
    this.selectItemChange.emit(event);
  }

  clearValue() {
    this.filter = '';
    this.value = null;
    this.onChangeCallback(null);
    this.onSelectItemChange(null);
  }

  public onOverlayClick(event: MouseEvent) {
    event.stopPropagation();
    return false;
  }

  onShowOverlay() {
    if (this.disabled || this.readonly) {
      this.showOverlay = false;
    } else {
      this.showOverlay = true;
      this.table.refreshItems();
    }
  }

  onHideOverlay() {
    this.showOverlay = false;
  }

  @HostListener('document:click')
  private onDocumentClick() {
    this.showOverlay = false;
  }

  onKeyUp(event: KeyboardEvent) {
    const table = this.table;

    switch (event.keyCode) {
      // Arrow Up
      case 38 : {
        if (table.hoveredIndex > 0) {
          table.hoveredIndex--;
        } else {

        }
      }
        break;

      // Arrow Down
      case 40 : {
        if (table.hoveredIndex === undefined) {
          table.hoveredIndex = table.scrollWindowChange.startIndex;
        } else {
          if (table.hoveredIndex < table.scrollWindowChange.startIndex) {
            table.hoveredIndex = table.scrollWindowChange.startIndex;
          } else {
            table.hoveredIndex++;
          }
        }
      }
        break;
    }
  }

  writeValue(obj: any): void {
    if (obj) {
      this.value = obj;

      let itemName = this.itemName(this.value);
      if (itemName) {
        this.filter = itemName;
      } else {
        const search : Search = {
          selected : obj
        };

        const callback = (data: [any], size: number) => {
          if (this.filter.length === 0) {
            this.filter = this.itemName(data[0]);
            this.stateChanges.next();
          }
        };
        this.items(search, callback);
      }

      this.stateChanges.next();
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = (value : any) => {
      fn(value);
      this.stateChanges.next();
    };
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

  ngOnDestroy() {
    this.stateChanges.complete();
  }
}
