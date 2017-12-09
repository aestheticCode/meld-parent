import {
  Component, ContentChild, EventEmitter, forwardRef, HostListener, Input, OnChanges, Output, SimpleChanges, TemplateRef,
  ViewChild
} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {Items} from "../../common/query/Items";
import {MeldTableComponent} from "../meld-table/meld-table.component";
import {QueryBuilder} from "../../common/query/QueryBuilder";

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
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class MeldComboBoxComponent implements OnChanges, ControlValueAccessor {

  public value: any;

  public size: number = 0;

  public showInfo: boolean = false;

  public showOverlay: boolean = false;

  @Input("filter")
  public filter: string = "";

  @Input("placeholder")
  public placeholder: string = "";

  @Input("items")
  public items: Items<any>;

  @Input("readonly")
  public readonly : boolean = false;

  @Input("disabled")
  public disabled : boolean = false;

  @Output('selectItemChange')
  private selectItemChange: EventEmitter<any> = new EventEmitter<any>();

  @ContentChild(TemplateRef)
  public template: TemplateRef<any>;

  @ViewChild("table")
  public table: MeldTableComponent;

  private onTouchedCallback: () => void = noop;

  private onChangeCallback: (value: any) => void = noop;

  public parentItems: Items<any> = (query, callback) => {
    if (this.value) {
      const response = (data: [any], size: number) => {
        if (this.filter.length === 0) {
          this.filter = this.itemName(data[0]);
        }
      };
      query.predicate = this.predicate(this.filter);
      this.items(query, response);
    } else {
      const response = (data: [any], size: number) => {
        callback(data, size);
        this.size = size;
      };
      query.predicate = this.predicate(this.filter);
      this.items(query, response);
    }
  };

  @Input("predicate")
  public predicate = (value) => {
    if (value == null) {
      return null;
    }
    return QueryBuilder.like(value, "name");
  };

  @Input("itemValue")
  public itemValue = (item) => {
    if (item == null) {
      return null;
    }
    return item['id'];
  };

  @Input("itemName")
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
    this.showOverlay = true;
    this.filter = event;
    this.table.refreshItems();
    this.value = null;
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
    }
  }

  onHideOverlay() {
    this.showOverlay = false;
  }

  @HostListener("document:click")
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
          table.hoveredIndex = table.scrollWindowChange.startIndex
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

      let meldQuery = QueryBuilder.query();
      meldQuery.predicate = QueryBuilder.in([obj], "id");
      this.parentItems(meldQuery, () => {
      });
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }
}
