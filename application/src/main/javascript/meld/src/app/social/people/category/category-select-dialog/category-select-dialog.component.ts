import {Component, Inject, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Category} from '../categories.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {Selects} from '../../../../../lib/component/meld-combobox/meld-combobox.interfaces';

@Component({
  selector: 'app-category-dialog',
  templateUrl: 'category-select-dialog.component.html',
  styleUrls: ['category-select-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CategorySelectDialogComponent {

  constructor(private http: HttpClient,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<CategorySelectDialogComponent>) {
  }

  categories: Selects<Category> = (search, response) => {

    const params = {
      index: search.index.toString(),
      limit: search.limit.toString()
    };

    this.http.get<Container<Category>>('service/social/people/categories', {params: params})
      .subscribe((res: Container<Category>) => {
        response(res.rows, res.size);
      });
  };

  onSave() {
    this.dialogRef.close(this.data);
  }

  onCancel() {
    this.dialogRef.close();
  }
}
