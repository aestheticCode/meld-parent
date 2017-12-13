import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MatDialog} from '@angular/material';
import {Items} from '../../../../../lib/common/query/Items';
import {UserRow} from '../find.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {CategoryDialogComponent} from '../../category/category-dialog/category-dialog.component';

@Component({
  selector: 'app-social-find-view',
  templateUrl: 'find-view.component.html',
  styleUrls: ['find-view.component.css']
})
export class FindViewComponent {

  constructor(private http: HttpClient,
              private dialog : MatDialog) {}

  users: Items<UserRow> = (query, response) => {
    this.http.post<Container<UserRow>>('service/social/people/find', query)
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };

  open(user : UserRow) {
    let matDialogRef = this.dialog.open(CategoryDialogComponent, {data : user});

    matDialogRef.afterClosed().subscribe((result) => {
      this.http.put<UserRow>('service/social/people/find', result)
        .subscribe((res: UserRow) => {});
    })
  }


}
