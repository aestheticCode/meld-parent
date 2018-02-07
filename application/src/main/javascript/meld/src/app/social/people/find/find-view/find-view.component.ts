import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {Container} from 'lib/common/rest/Container';
import {CategoryDialogComponent} from '../../category/category-dialog/category-dialog.component';
import {Items} from 'lib/common/search/search.interfaces';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {MeldTableComponent} from 'lib/component/meld-table/meld-table.component';
import {Filter, UserRow} from '../find.interfaces';
import {JsonConvert} from 'json2typescript';
import {
  AndExpressionModel, FilterModel, NameExpressionModel, NormalExpressionModel, SchoolExpressionModel,
  SearchModel
} from '../find.classes';

@Component({
  selector: 'app-social-find-view',
  templateUrl: 'find-view.component.html',
  styleUrls: ['find-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FindViewComponent {

  public filters: Filter[] = [];

  @ViewChild('table')
  private table: MeldTableComponent;

  constructor(private http: HttpClient,
              private router: MeldRouterService,
              private dialog: MatDialog) {
    this.filters = [
      new FilterModel("name", new NameExpressionModel("")),
      new FilterModel("school", new SchoolExpressionModel(""))
    ]
  }

  users: Items<UserRow> = (query, response) => {

    const search = new SearchModel(
      query.index,
      query.limit,
      new AndExpressionModel(this.filters.filter((filter) => filter.active).map((filter) => filter.expression)),
      [new NormalExpressionModel("firstName", true)]
    );

    this.http.post<Container<UserRow>>('service/social/people/find', search)
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };

  onSearch() {
    this.table.refreshItems();
  }

  open(user: UserRow) {
    let matDialogRef = this.dialog.open(CategoryDialogComponent, {data: user});

    matDialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.http.put<UserRow>('service/social/people/find', result)
          .subscribe((res: UserRow) => {
          });
      }
    });
  }

}
