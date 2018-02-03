import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {UserRow} from '../find.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {CategoryDialogComponent} from '../../category/category-dialog/category-dialog.component';
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';
import {FilterFormComponent} from './filter-form/filter-form.component';
import {Strings} from '../../../../../lib/common/utils/Strings';
import {MeldTableComponent} from '../../../../../lib/component/meld-table/meld-table.component';

@Component({
  selector: 'app-social-find-view',
  templateUrl: 'find-view.component.html',
  styleUrls: ['find-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FindViewComponent {

  public expression: any;

  @ViewChild('filter')
  private filter: FilterFormComponent;

  @ViewChild('table')
  private table: MeldTableComponent;

  constructor(private http: HttpClient,
              private router: MeldRouterService,
              private dialog: MatDialog) {
  }

  users: Items<UserRow> = (query, response) => {
    query.expression = this.expression;
    this.http.post<Container<UserRow>>('service/social/people/find', query)
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };

  onSearch() {
    let andExpression = [];

    if (Strings.isNotEmpty(this.filter.name)) {
      andExpression.push({
        type: 'name',
        value: this.filter.name
      });
    }

    if (Strings.isNotEmpty(this.filter.school)) {
      andExpression.push({
        type: 'school',
        value: this.filter.school
      });
    }

    this.expression = {
      type: 'and',
      value: andExpression
    };

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
