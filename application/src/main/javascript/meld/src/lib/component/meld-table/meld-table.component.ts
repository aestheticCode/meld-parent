import {
  AfterContentInit,
  Component,
  ContentChild,
  EventEmitter,
  forwardRef,
  Input,
  OnInit,
  Output
} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {MatCheckboxChange, MatDialog} from '@angular/material';
import {MeldTableMenuDialogComponent} from "./meld-menu-dialog/meld-table-menu-dialog.component";
import {MeldFilterDirective} from "./meld-filter/meld-filter.directive";
import {MeldColgroupDirective} from "./meld-colgroup/meld-colgroup.directive";
import {MeldTheadDirective} from "./meld-thead/meld-thead.directive";
import {MeldTbodyDirective} from "./meld-tbody/meld-tbody.directive";
import {MeldTfooterDirective} from "./meld-tfooter/meld-tfooter.directive";
import {MeldTableFilterDialogComponent} from "./meld-filter-dialog/meld-table-filter-dialog.component";
import {LoadWindow} from "../meld-window/LoadWindow";
import {TableColumn} from "./TableColumn";
import {MeldTdDirective} from './meld-td/meld-td.directive';
import {Items} from '../../common/search/search.interfaces';
import {QueryBuilder, Sort} from '../../common/search/search.classes';

const noop = () => {
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldTableComponent),
  multi: true
};

@Component({
  selector: 'meld-table',
  templateUrl: 'meld-table.component.html',
  styleUrls: ['meld-table.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class MeldTableComponent implements OnInit, AfterContentInit, ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  public value: any[] = [];

  @Output('selectItemChange')
  private selectItemChange: EventEmitter<any> = new EventEmitter<any>();

  @Input('multiSelect')
  public multiSelect: boolean = false;

  @Input('menu')
  public menuSymbol: boolean = false;

  @Input('filter')
  public filterSymbol: boolean = false;

  public allSelected: boolean = false;

  @Input("unbordered")
  public unbordered: boolean = false;

  public hoveredIndex: number = undefined;

  public columnConfiguration: TableColumn[] = [];

  public itemsWindow: any[] = [];

  public itemsSize: number;

  public scrollWindowChange: LoadWindow = new LoadWindow(0, 75, 0, 5);

  @Input('items')
  private items: Items<any>;

  @Input('rowHeight')
  public rowHeight: number = 37;

  public sorting: Sort[];

  @Input('initialize')
  public initialize : boolean = true;

  @ContentChild(MeldFilterDirective)
  public filter: MeldFilterDirective;

  @ContentChild(MeldColgroupDirective)
  public colgroup: MeldColgroupDirective;

  @ContentChild(MeldTheadDirective)
  public head: MeldTheadDirective;

  @ContentChild(MeldTbodyDirective)
  public body: MeldTbodyDirective;

  @ContentChild(MeldTfooterDirective)
  public footer: MeldTfooterDirective;

  @Input('itemValue')
  public itemValue = (item) => {
    return item['id'];
  };

  constructor(public dialog: MatDialog) {
  }

  ngOnInit(): void {
    if (this.initialize) {
      this.refreshItems();
    }
  }

  public refreshItems() {
    if (this.items instanceof Function) {
      let query = QueryBuilder.query();
      query.index = 0;
      query.limit = 75;
      this.items(query, (data: any[], size: number) => {
        this.itemsWindow = data;
        this.itemsSize = size;
      });
    }
  }

  onWindowScroll(event: LoadWindow) {
    this.scrollWindowChange = event;
    if (this.items instanceof Function) {
      let query = QueryBuilder.query();
      query.index = event.loadIndex;
      query.limit = event.loadLimit * 5;
      query.sorting = this.sorting;
      this.items(query, (data: any[], size: number) => {
        this.itemsWindow = data;
        this.itemsSize = size;
        if (event.callback) {
          event.callback();
        }
      });
    }
  }

  ngAfterContentInit(): void {
    if (this.colgroup instanceof MeldColgroupDirective) {
      this.columnConfiguration = this.colgroup.columns.map((col, index) => {
        if (this.head) {
          this.head.rows.first.columns.toArray()[index].path = col.path;
        }
        return new TableColumn(index, col.visible, col.width, col.path)
      })
    } else {
      if (this.body) {
        let rows = this.body.rows;
        if (rows) {
          let firstRow = rows.first;
          if (firstRow) {
            let columns = firstRow.columns;
            if (columns) {
              this.columnConfiguration = columns.map((col, index) => {
                return new TableColumn(index, true, 100, "");
              })
            }
          }
        }
      }
    }
  }

  onSortClick(column : MeldTdDirective) {

    if (column.asc === true) {
      column.asc = false;
    } else
    if (column.asc === false) {
      column.asc = undefined;
    } else
    if (column.asc === undefined) {
      column.asc = true;
    }

    this.sorting = this.head.rows.first.columns
      .filter((column) => column.asc !== undefined)
      .map((column) => new Sort(column.path, column.asc));

    this.onWindowScroll(this.scrollWindowChange);
  }

  getIdFromItem(item: any) {
    return this.itemValue(item);
  }

  isItemSelected(item: any) {
    return this.value.indexOf(this.getIdFromItem(item)) != -1;
  }

  selectItem(item: any) {
    this.value.push(this.getIdFromItem(item));
    this.onChangeCallback(this.value);
  }

  deSelectItem(item: any) {
    let indexOf = this.value.indexOf(this.getIdFromItem(item));
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

  isHoveredIndex(index) {
    return this.hoveredIndex === index;
  }

  selectAll(checked: MatCheckboxChange) {
    this.allSelected = checked.checked;
    this.value = [];

    if (checked.checked) {
      let query = QueryBuilder.query();
      query.index = 0;
      query.limit = 0x7fffffff;
      this.items(query, (data: any[], size: number) => {
        this.value = data.map((data) => {
          return this.getIdFromItem(data);
        });

        this.onChangeCallback(this.value);
      });
    } else {
      this.onChangeCallback(this.value);
    }
  }

  writeValue(obj: any): void {
    if (obj) {
      this.value = obj;
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

  openMenu() {
    let dialogRef = this.dialog.open(MeldTableMenuDialogComponent, {data: this});
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
    });
  }

  openFilter() {
    let dialogRef = this.dialog.open(MeldTableFilterDialogComponent,{data: this.filter});
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
    });
  }

}
