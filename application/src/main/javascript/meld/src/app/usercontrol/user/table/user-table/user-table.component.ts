import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {Http, Response} from '@angular/http';
import {FilterTemplate, UserRow} from './user-table.interfaces';
import {FilterTemplateModel} from './user-table.classes';
import {NgModel} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {MeldTableComponent} from 'lib/component/meld-table/meld-table.component';
import {Link} from 'lib/common/rest/Link';
import {Strings} from 'lib/common/utils/Strings';
import {Objects} from 'lib/common/utils/Objects';
import {Container} from 'lib/common/rest/Container';
import {LevenstheinSort, QueryBuilder} from 'lib/common/search/search.classes';
import {RestExpression} from 'lib/common/search/expression.interfaces';
import {Items, SortExpression} from '../../../../../lib/common/search/search.interfaces';

@Component({
  selector: 'app-user-table',
  templateUrl: 'user-table.component.html',
  styleUrls: ['user-table.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class UserTableComponent implements OnInit {

  @Input('filter')
  public filter: RestExpression;

  @Input('sort')
  public sort : SortExpression;

  public filterTemplate: FilterTemplate = new FilterTemplateModel();

  @ViewChild('table')
  private table: MeldTableComponent;

  public links: Link[] = [];

  @ViewChild('searchBox')
  private searchBox: NgModel;


  constructor(private http: Http,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.data.forEach((data: { users: Container<UserRow> }) => {
      this.links = data.users.links;
    });

    this.searchBox
      .control
      .valueChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .subscribe((event: string) => {
        this.sort = new LevenstheinSort(event, ['firstName', 'lastName'], false);
        this.table.refreshItems();
      });

    let matchMedia = window.matchMedia('(max-width: 1000px)');

    if (matchMedia.matches) {
      this.table.columnConfiguration.forEach((column) => {
        if (column.index === 3) {
          column.visible = false;
        }
      });
    }

    matchMedia.addListener((listener) => {
      this.table.columnConfiguration.forEach((column) => {
        if (column.index === 3) {
          column.visible = false;
        }
      });
    });


    window.matchMedia('(max-width: 1050px)').addListener((listener) => {
      this.table.columnConfiguration.forEach((column) => {
        if (column.index === 3) {
          column.visible = true;
        }
      });
    });

  }


  onCancelClick() {
    this.filterTemplate = new FilterTemplateModel();
    this.filter = undefined;
    this.table.refreshItems();
  }

  onSearchClick() {
    const result: RestExpression[] = [];

    if (Strings.isNotEmpty(this.filterTemplate.firstName)) {
      result.push(QueryBuilder.path('firstName', QueryBuilder.like(this.filterTemplate.firstName)));
    }

    if (Strings.isNotEmpty(this.filterTemplate.lastName)) {
      result.push(QueryBuilder.path('lastName', QueryBuilder.like(this.filterTemplate.lastName)));
    }

    if (Objects.isNotNull(this.filterTemplate.birthdate)) {

    }

    if (result.length > 0) {
      this.filter = QueryBuilder.or(result);
    } else {
      this.filter = undefined;
    }

    this.table.refreshItems();
  }

  users: Items<UserRow> = (query, response) => {
    query.expression = this.filter;
    if (this.sort) {
      query.sorting.unshift(this.sort);
    }
    this.http.post('service/usercontrol/user/table', query)
      .subscribe((res: Response) => {
        const json = res.json() as Container<UserRow>;
        response(json.rows, json.size);
      });
  };

  onSelectionChange(user: UserRow) {
    if (user.links.find((link) => link.rel === 'read')) {
      this.router.navigate(['usercontrol/user', user.id, 'view']);
    }
  }

  onCreate() {
    this.router.navigate(['usercontrol/user']);
  }


}
