import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NgModel} from '@angular/forms';
import {Http, Response} from '@angular/http';
import {ActivatedRoute, Router} from '@angular/router';
import {GroupRow} from './group-table.interfaces';
import {Link} from '../../../../../lib/common/rest/Link';
import {RestExpression} from '../../../../../lib/common/search/expression.interfaces';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {HttpClient} from '@angular/common/http';
import {MeldTableComponent} from '../../../../../lib/component/meld-table/meld-table.component';

@Component({
  selector: 'app-group-table',
  templateUrl: 'group-table.component.html',
  styleUrls: ['group-table.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class GroupTableComponent implements OnInit {

  filter: string;

  links: Link[] = [];

  @ViewChild('searchBox')
  searchBox: NgModel;

  @ViewChild("grid")
  grid : MeldTableComponent

  constructor(private http: HttpClient,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.data.forEach((data: { groups: any }) => {
      this.links = data.groups.links;
    });

    this.searchBox
      .control
      .valueChanges
      .debounceTime(300)
      .subscribe((event) => {
        this.filter = event;
        this.grid.refreshItems();
      });
  }

  groups: Items<GroupRow> = (query, response) => {

    const params = {
      index : query.index.toString(),
      limit : query.limit.toString(),
      sort : query.sort
    };

    if (this.filter) {
      params['name'] = this.filter;
    }

    this.http.get<Container<GroupRow>>('service/usercontrol/group/table', {params : params})
      .subscribe((res) => {
        response(res.rows, res.size);
      });
  };

  onSelection(group: GroupRow) {
    if (group.links.find((link) => link.rel === 'read')) {
      this.router.navigate(['usercontrol/group', group.id]);
    }
  }

  onCreate() {
    this.router.navigate(['usercontrol/group']);
  }

}
