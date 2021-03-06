import {
  Component,
  ContentChild,
  ElementRef,
  EventEmitter,
  HostBinding,
  HostListener,
  Input,
  OnChanges,
  Optional,
  Output,
  Self,
  SimpleChanges,
  TemplateRef,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {NgControl} from '@angular/forms';
import {MeldTableComponent} from '../meld-table/meld-table.component';
import {Subject} from 'rxjs/Subject';
import {Items} from '../../common/search/search.interfaces';
import {Search, Selects} from './meld-combobox.interfaces';
import {MatFormFieldControl} from '@angular/material';
import {AbstractControl} from '../../common/forms/AbstractControl';
import {Strings} from '../../common/utils/Strings';

const noop = () => {
};

@Component({
  selector: 'meld-combobox',
  templateUrl: 'meld-combobox.component.html',
  styleUrls: ['meld-combobox.component.css'],
  providers: [{provide: MatFormFieldControl, useExisting: MeldComboBoxComponent}],
  encapsulation: ViewEncapsulation.None
})
export class MeldComboBoxComponent extends AbstractControl<any> implements OnChanges {

  static nextId = 0;

  @HostBinding()
  id = `meld-combobox-${MeldComboBoxComponent.nextId++}`;

  get focused() {
    return document.activeElement === this.input.nativeElement;
  }

  onContainerClick(event: MouseEvent): void {
    this.input.nativeElement.focus();
  }

  get empty() {
    return Strings.isEmpty(this.filter);
  }


  public value: any;

  public size: number = 0;

  public showInfo: boolean = false;

  public showOverlay: boolean = false;

  @Input('filter')
  public filter: string = '';

  @Output('filterChange')
  public filterChange: EventEmitter<string> = new EventEmitter<string>();

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

  @ViewChild('input')
  public input: ElementRef<HTMLInputElement>;

  private filterChanges = new Subject<string>();

  private onTouchedCallback: () => void = noop;

  private onChangeCallback: (value: any) => void = noop;

  constructor(private elRef: ElementRef,
              @Optional() @Self() public ngControl: NgControl) {
    super(ngControl);
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

  focus() {
    this.input.nativeElement.focus();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.table && changes['itemValuePath']) {
      this.table.itemValue = this.itemValue;
    }
  }

  public onFilterChange(event) {
    this.filterChanges.next(event);
  }

  public onSelectItemChange(event) {
    this.value = event;
    this.filter = this.itemName(event);
    this.filterChange.emit(this.filter);
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

  public onOverlayClick(event: MouseEvent) {
    event.stopPropagation();
    return false;
  }

  onShowOverlay() {
    if (this.disabled || this.readonly) {
      this.showOverlay = false;
    } else {
      this.showOverlay = true;
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

    switch (event.keyCode) {
      case 13 : {
        let item = this.table.itemsWindow[this.table.hoveredIndex];
        let itemName = this.itemName(item);
        this.value = item;
        this.filter = itemName;
        this.filterChange.emit(itemName);
        this.onChangeCallback(this.value);
        this.showOverlay = false;
        this.selectItemChange.emit(item);
        this.table.refreshItems();
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
