import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {Http, Response} from '@angular/http';
import {FilterTemplate, UserRow} from './user-table.interfaces';
import {FilterTemplateModel} from './user-table.classes';
import {NgModel} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {MeldTableComponent} from 'lib/component/meld-table/meld-table.component';
import {Link} from 'lib/common/rest/Link';
import {Container} from 'lib/common/rest/Container';
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-user-table',
  templateUrl: 'user-table.component.html',
  styleUrls: ['user-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserTableComponent implements OnInit {

  @Input('filter')
  public filter: string;

  public filterTemplate: FilterTemplate = new FilterTemplateModel();

  @ViewChild('table')
  private table: MeldTableComponent;

  public links: Link[] = [];

  @ViewChild('searchBox')
  private searchBox: NgModel;


  constructor(private http: HttpClient,
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
        this.filter = event;
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

  users: Items<UserRow> = (query, response) => {

    const params = {
      index: query.index.toString(),
      limit: query.limit.toString(),
      name: this.filter,
      sort: query.sort
    };

    this.http.get<Container<UserRow>>('service/usercontrol/user/table', {params: params})
      .subscribe((res) => {
        response(res.rows, res.size);
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
