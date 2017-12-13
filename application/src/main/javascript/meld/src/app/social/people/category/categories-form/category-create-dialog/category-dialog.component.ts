import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-social-category-dialog',
  templateUrl: 'category-dialog.component.html',
  styleUrls: ['category-dialog.component.css']
})
export class CategoryCreateDialogComponent {

  name: string;

  constructor(public dialogRef: MatDialogRef<CategoryCreateDialogComponent>) {
  }

  onCreate() {
    this.dialogRef.close(this.name);
  }

  onCancel() {
    this.dialogRef.close();
  }

}
