import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {Container} from '@aestheticcode/meld-lib';
import {Items} from '@aestheticcode/meld-lib';
import {MeldRouterService} from '@aestheticcode/meld-lib';
import {MeldTableComponent} from '@aestheticcode/meld-lib';
import {School} from '../../../../profile/education/school-form.interfaces';
import {Address} from '../../../../profile/places/address.interfaces';
import {Company} from '../../../../profile/work-history/company.interfaces';
import {Filter, UserRow} from '../../find.interfaces';
import {CategorySelectDialogComponent} from '../../../category/select/category-select-dialog/category-select-dialog.component';

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
