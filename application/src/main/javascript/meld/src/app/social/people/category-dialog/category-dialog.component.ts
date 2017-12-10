import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Items} from '../../../../lib/common/query/Items';
import {Category} from '../categories-select/categories-select.interfaces';
import {Container} from '../../../../lib/common/rest/Container';

@Component({
  selector: 'app-category-dialog',
  templateUrl: 'category-dialog.component.html',
  styleUrls: ['category-dialog.component.css']
})
export class CategoryDialogComponent {

  user : any;

  constructor(private http : HttpClient,
              @Inject(MAT_DIALOG_DATA) data: any,
              public dialogRef: MatDialogRef<CategoryDialogComponent>) {
    this.user = data;
  }

  categories: Items<Category> = (query, response) => {
    this.http.post<Container<Category>>('service/social/people/categories', query)
      .subscribe((res: Container<Category>) => {
        response(res.rows, res.size);
      });
  };

  onSave() {
    this.dialogRef.close(this.user);
  }

  onCancel() {
    this.dialogRef.close();
  }
}