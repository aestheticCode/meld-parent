import {Component, ViewEncapsulation} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MatDialog} from '@angular/material';
import {UserRow} from '../find.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {CategoryDialogComponent} from '../../category/category-dialog/category-dialog.component';
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';
import {Education} from '../../../profile/education/education.interfaces';
import {Places} from '../../../profile/places/places.interfaces';
import {WorkHistory} from '../../../profile/work-history/work-history.interfaces';
import {RestExpression} from '../../../../../lib/common/search/expression.interfaces';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';

@Component({
  selector: 'app-social-find-view',
  templateUrl: 'find-view.component.html',
  styleUrls: ['find-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class FindViewComponent {

  public queries : RestExpression[];

  constructor(private http: HttpClient,
              private router : MeldRouterService,
              private dialog : MatDialog) {
    this.queries = this.router.data.meta;
  }

  users: Items<UserRow> = (query, response) => {
    query.expression = QueryBuilder.and(this.queries);
    this.http.post<Container<UserRow>>('service/social/people/find', query)
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };

  open(user : UserRow) {
    let matDialogRef = this.dialog.open(CategoryDialogComponent, {data : user});

    matDialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.http.put<UserRow>('service/social/people/find', result)
          .subscribe((res: UserRow) => {});
      }
    })
  }


}
