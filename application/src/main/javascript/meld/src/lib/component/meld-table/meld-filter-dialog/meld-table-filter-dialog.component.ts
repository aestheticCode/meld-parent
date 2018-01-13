import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material";
import {MeldFilterDirective} from "../meld-filter/meld-filter.directive";

@Component({
  selector: 'meld-table-filter-dialog',
  templateUrl: 'meld-table-filter-dialog.component.html',
  styleUrls: ['meld-table-filter-dialog.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldTableFilterDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public filter: MeldFilterDirective) {}

  ngOnInit() {
  }

}
