import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NgModel} from '@angular/forms';
import {Http, Response} from '@angular/http';
import {ActivatedRoute, Router} from '@angular/router';
import {GroupRow} from './group-table.interfaces';
import {Link} from '../../../../../lib/common/rest/Link';
import {RestExpression} from '../../../../../lib/common/search/expression.interfaces';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';
import {Items} from '../../../../../lib/common/search/search.interfaces';

@Component({
  selector: 'app-group-table',
  templateUrl: 'group-table.component.html',
  styleUrls: ['group-table.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class GroupTableComponent implements OnInit {

  filter: RestExpression;

  links: Link[] = [];

  @ViewChild('searchBox') searchBox: NgModel;

  constructor(private http: Http,
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
        this.filter = QueryBuilder.path('name', QueryBuilder.like(event));
      });
  }

  groups: Items<GroupRow> = (query, response) => {
    query.expression = this.filter;
    this.http.post('service/usercontrol/group/table', query)
      .subscribe((res: Response) => {
        const json = res.json();
        response(json.rows, json.size);
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
