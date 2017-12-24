import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Category} from '../categories.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {Selects} from '../../../../../lib/component/meld-combobox/meld-combobox.interfaces';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';

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

  categories: Selects<Category> = (search, response) => {
    let query = QueryBuilder.query();
    query.index = search.index;
    query.limit = search.limit;
    if (search.selected) {
      query.expression = QueryBuilder.path("id", QueryBuilder.equal(search.selected));
    } else {
      query.expression = QueryBuilder.path("name", QueryBuilder.like(search.filter));
    }
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
