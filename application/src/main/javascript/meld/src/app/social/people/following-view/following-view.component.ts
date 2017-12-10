import {Component} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserRow} from '../find-view/find-view.interfaces';
import {HttpClient} from '@angular/common/http';
import {Items} from '../../../../lib/common/query/Items';
import {Container} from '../../../../lib/common/rest/Container';
import {CategoryDialogComponent} from '../category-dialog/category-dialog.component';
import {AppService} from '../../../app.service';
import {QueryBuilder} from '../../../../lib/common/query/QueryBuilder';

@Component({
  selector: 'app-following-view',
  templateUrl: 'following-view.component.html',
  styleUrls: ['following-view.component.css']
})
export class FollowingViewComponent {

  constructor(private http: HttpClient,
              private service : AppService,
              private dialog: MatDialog) {
  }

  users: Items<UserRow> = (query, response) => {
    let equal = QueryBuilder.equal(this.service.configuration.user.id, "to.id");
    query.predicate = QueryBuilder.subQuery(equal, "user", 'relationShip', 'from');
    this.http.post<Container<UserRow>>('service/social/people/find', query)
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };

  open(user: UserRow) {
    let matDialogRef = this.dialog.open(CategoryDialogComponent, {data: user});

    matDialogRef.afterClosed().subscribe((result) => {
      this.http.put<UserRow>('service/social/people/find', result)
        .subscribe((res: UserRow) => {
        });
    });
  }


}
