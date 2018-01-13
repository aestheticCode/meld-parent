import {Component, Inject, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material";

@Component({
  selector: 'meld-table-menu-dialog',
  templateUrl: 'meld-table-menu-dialog.component.html',
  styleUrls: ['meld-table-menu-dialog.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldTableMenuDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public parent: any) {}

  colGroupWithIndex(index: number) {
    return this.parent.colgroup.columns.toArray()[this.parent.columnConfiguration[index].index];
  }

  colConfigurationWithIndex(index: number) {
    return this.parent.columnConfiguration[index];
  }

  allowDrop(event: DragEvent) {
    event.preventDefault();
  }

  drag(event: DragEvent, index: number) {
    event.dataTransfer.setData("index", index.toString());
  }

  drop(event: DragEvent, index: number) {
    event.preventDefault();
    let indexString = event.dataTransfer.getData("index");
    let dragIndex = Number(indexString);
    let tableColumns = this.parent.columnConfiguration;
    let main = tableColumns.slice(dragIndex, dragIndex + 1);
    tableColumns.splice(dragIndex, 1);

    let start = tableColumns.slice(0, index);
    let end = tableColumns.slice(index, tableColumns.length);
    let result = [];
    this.parent.columnConfiguration = result.concat(start, main, end);
  }


}
