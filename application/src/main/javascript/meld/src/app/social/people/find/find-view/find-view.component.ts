import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {Container} from 'lib/common/rest/Container';
import {CategoryDialogComponent} from '../../category/category-dialog/category-dialog.component';
import {Items} from 'lib/common/search/search.interfaces';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {MeldTableComponent} from 'lib/component/meld-table/meld-table.component';
import {Filter, UserRow} from '../find.interfaces';
import {FilterModel, NameExpressionModel, SchoolExpressionModel} from '../find.classes';
import {URLSearchParams} from '@angular/http';

@Component({
  selector: 'app-social-find-view',
  templateUrl: 'find-view.component.html',
  styleUrls: ['find-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FindViewComponent {

  public filters: Filter[] = [];

  public name : string;

  public school : string;

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

    const params: any = {
      index : query.index,
      limit : query.limit,
    };

    if (this.school) {
      params.school = this.school;
    }

    if (this.name) {
      params.name = this.name;
    }

    this.http.get<Container<UserRow>>('service/social/people/find', { params : params})
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
