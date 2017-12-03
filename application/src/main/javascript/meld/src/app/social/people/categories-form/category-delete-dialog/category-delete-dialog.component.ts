import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-category-delete-dialog',
  templateUrl: 'category-delete-dialog.component.html',
  styleUrls: ['category-delete-dialog.component.css']
})
export class CategoryDeleteDialogComponent {

  constructor(public dialogRef: MatDialogRef<CategoryDeleteDialogComponent>) {
  }

  onDelete() {
    this.dialogRef.close(true);
  }

  onCancel() {
    this.dialogRef.close(false);
  }

}
