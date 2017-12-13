import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-category-rename-dialog',
  templateUrl: 'category-rename-dialog.component.html',
  styleUrls: ['category-rename-dialog.component.css']
})
export class CategoryRenameDialogComponent {

  name: string;

  constructor(public dialogRef: MatDialogRef<CategoryRenameDialogComponent>) {
  }

  onRename() {
    this.dialogRef.close(this.name);
  }

  onCancel() {
    this.dialogRef.close(false);
  }

}
