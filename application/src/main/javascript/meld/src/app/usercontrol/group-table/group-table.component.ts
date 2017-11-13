import {Component, OnInit, ViewChild} from '@angular/core';
import {Predicate} from "../../../lib/common/predicates/Predicate";
import {Link} from "../../../lib/common/rest/Link";
import {NgModel} from "@angular/forms";
import {Http, Response} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {QueryBuilder} from "../../../lib/common/query/QueryBuilder";
import {GroupRow} from "./GroupRow";
import {Items} from "../../../lib/common/query/Items";

@Component({
  selector: 'app-group-table',
  templateUrl: 'group-table.component.html',
  styleUrls: ['group-table.component.css']
})
export class GroupTableComponent implements OnInit {

  filter: Predicate<any>;

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
        this.filter = QueryBuilder.like(event, "name");
      });
  }

  groups: Items<GroupRow> = (query, response) => {
    query.predicate = this.filter;
    this.http.post('service/usercontrol/group/table', query)
      .subscribe((res: Response) => {
        const json = res.json();
        response(json.rows, json.size);
      });
  };

  onSelection(group: GroupRow) {
    if (group.links.find((link) => link.rel === "read")) {
      this.router.navigate(['usercontrol/group', group.id]);
    }
  }

  onCreate() {
    this.router.navigate(['usercontrol/group']);
  }

}
