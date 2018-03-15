import {
  Component,
  ContentChild,
  EventEmitter,
  forwardRef,
  HostListener,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
  TemplateRef,
  ViewChild, ViewEncapsulation
} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {MeldComboBoxComponent} from "../meld-combobox/meld-combobox.component";
import {MeldTableComponent} from "../meld-table/meld-table.component";
import {Selects} from '../meld-combobox/meld-combobox.interfaces';

const noop = () => {
};

const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldMultiSelectComponent),
  multi: true
};

@Component({
  selector: 'meld-multiselect',
  templateUrl: 'meld-multiselect.component.html',
  styleUrls: ['meld-multiselect.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
  encapsulation : ViewEncapsulation.None
})
export class MeldMultiSelectComponent implements OnChanges, ControlValueAccessor {

  public value: any[] = [];

  public showOverlay: boolean = false;

  public showInfo: boolean = false;

  @Input("hideComboBox")
  public hideComboBox : boolean = false;

  @Output('selectItemChange')
  private selectItemChange: EventEmitter<any> = new EventEmitter<any>();

  @Input("placeholder")
  public placeholder: string;

  @Input("items")
  public items: Selects<any>;

  @Input("disabled")
  public disabled : boolean = false;

  @Input("readonly")
  public readonly : boolean = false;

  @ViewChild("comboBox")
  public comboBox: MeldComboBoxComponent;

  @ViewChild("table")
  public table: MeldTableComponent;

  @ContentChild(TemplateRef)
  public template: TemplateRef<any>;

  private onTouchedCallback: () => void = noop;

  private onChangeCallback: (value: any) => void = noop;

  selectedItems: Selects<any> = (query, response) => {
    response(this.value, this.value.length);
  };

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['placeholder']) {
      if (this.comboBox) {
        this.comboBox.placeholder = this.placeholder;
      }
    }
  }

  refreshItems() {
    this.table.refreshItems();
  }

  onSelectItemChange(event) {
    const id = this.itemValue(event);
    if (this.value.find((item) => item.id === id) === undefined) {
      this.value.push(event);
      this.selectItemChange.emit(event);
      this.onChangeCallback(this.value);
      this.comboBox.value = null;
      this.table.refreshItems();
    }
    this.comboBox.filter = "";
  }

  @Input("itemValue")
  public itemValue = (item) => {
    return item['id'];
  };

  @Input("itemName")
  public itemName = (item) => {
    return item['name'];
  };

  @HostListener("document:click")
  private onDocumentClick() {
    this.showOverlay = false;
  }

  writeValue(obj: any): void {
    if (obj) {
      this.value = obj;

      this.table.refreshItems();
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

  onRemoveSelectedItem(item) {
    let indexOf = this.value.indexOf(item);
    this.value.splice(indexOf, 1);
    this.table.refreshItems();
    this.onChangeCallback(this.value);
  }
}
