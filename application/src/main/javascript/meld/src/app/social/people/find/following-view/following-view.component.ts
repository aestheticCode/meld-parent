import {Component, ViewEncapsulation} from '@angular/core';
import {MatDialog} from '@angular/material';
import {AppService} from '../../../../app.service';
import {HttpClient} from '@angular/common/http';
import {UserRow} from '../find.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {CategorySelectDialogComponent} from '../../category/category-select-dialog/category-select-dialog.component';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';
import {Items} from '../../../../../lib/common/search/search.interfaces';

@Component({
  selector: 'app-following-view',
  templateUrl: 'following-view.component.html',
  styleUrls: ['following-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class FollowingViewComponent {

  constructor(private http: HttpClient,
              private service: AppService,
              private dialog: MatDialog) {
  }

  users: Items<UserRow> = (query, response) => {
    this.http.get<Container<UserRow>>('service/social/people/find', {params : { follow : 'true'}})
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };

  open(user: UserRow) {
    let matDialogRef = this.dialog.open(CategorySelectDialogComponent, {data: user});

    matDialogRef.afterClosed().subscribe((result) => {
      this.http.put<UserRow>('service/social/people/find', result)
        .subscribe((res: UserRow) => {
        });
    });
  }


}
