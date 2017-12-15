import {Component} from '@angular/core';
import {MatDialog} from '@angular/material';
import {AppService} from '../../../../app.service';
import {HttpClient} from '@angular/common/http';
import {Items} from '../../../../../lib/common/query/Items';
import {UserRow} from '../find.interfaces';
import {QueryBuilder} from '../../../../../lib/common/query/QueryBuilder';
import {Container} from '../../../../../lib/common/rest/Container';
import {CategoryDialogComponent} from '../../category/category-dialog/category-dialog.component';

@Component({
  selector: 'app-following-view',
  templateUrl: 'following-view.component.html',
  styleUrls: ['following-view.component.css']
})
export class FollowingViewComponent {

  constructor(private http: HttpClient,
              private service: AppService,
              private dialog: MatDialog) {
  }

  users: Items<UserRow> = (query, response) => {
    query.predicate = QueryBuilder.and([
      QueryBuilder.not(
        QueryBuilder.inSelect(
          '',
          QueryBuilder.subQuery(
            'relationShip',
            'to', QueryBuilder.equal(this.service.configuration.user.id, 'from.id'))
        )
      ),
      QueryBuilder.inSelect(
        '',
        QueryBuilder.subQuery(
          'relationShip',
          'from', QueryBuilder.equal(this.service.configuration.user.id, 'to.id'))
      )
    ]);
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
