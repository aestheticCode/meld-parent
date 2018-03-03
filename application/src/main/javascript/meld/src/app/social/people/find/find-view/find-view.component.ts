import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {Container} from 'lib/common/rest/Container';
import {CategorySelectDialogComponent} from '../../category/category-select-dialog/category-select-dialog.component';
import {Items} from 'lib/common/search/search.interfaces';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {MeldTableComponent} from 'lib/component/meld-table/meld-table.component';
import {Filter, UserRow} from '../find.interfaces';
import {FilterModel, NameExpressionModel, SchoolExpressionModel} from '../find.classes';
import {School} from '../../../profile/education/school-form.interfaces';
import {Address} from '../../../profile/places/address.interfaces';
import {Company} from '../../../profile/work-history/company.interfaces';

@Component({
  selector: 'app-social-find-view',
  templateUrl: 'find-view.component.html',
  styleUrls: ['find-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FindViewComponent {

  public filters: Filter[] = [];

  public name: string;

  public school: School;

  public address : Address;

  public company: Company;

  @ViewChild('table')
  private table: MeldTableComponent;

  constructor(private http: HttpClient,
              private router: MeldRouterService,
              private dialog: MatDialog) {}

  users: Items<UserRow> = (query, response) => {

    const params: any = {
      index: query.index,
      limit: query.limit,
    };

    if (this.name) {
      params.name = this.name;
    }

    if (this.school) {
      params.school = this.school.id;
    }

    if (this.address) {
      params.address = this.address.id;
    }

    if (this.company) {
      params.company = this.company.id;
    }

    this.http.get<Container<UserRow>>('service/social/people/find', {params: params})
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };

  onSearch() {
    this.table.refreshItems();
  }

  open(user: UserRow) {
    let matDialogRef = this.dialog.open(CategorySelectDialogComponent, {data: user});

    matDialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.http.put<UserRow>('service/social/people/find', result)
          .subscribe((res: UserRow) => {
          });
      }
    });
  }

}
