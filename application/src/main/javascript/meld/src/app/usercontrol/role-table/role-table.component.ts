import {Component, OnInit, ViewChild} from '@angular/core';
import {QueryBuilder} from "../../../lib/common/query/QueryBuilder";
import {RoleRow} from "./RoleRow";
import {Link} from "../../../lib/common/rest/Link";
import {Predicate} from "../../../lib/common/predicates/Predicate";
import {NgModel} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Items} from "../../../lib/common/query/Items";
import {Http, Response} from "@angular/http";

@Component({
  selector: 'app-role-table',
  templateUrl: 'role-table.component.html',
  styleUrls: ['role-table.component.css']
})
export class RoleTableComponent implements OnInit {

  public filter: Predicate<any>;

  public links: Link[] = [];

  @ViewChild('searchBox')
  public searchBox: NgModel;

  constructor(private http : Http,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.data.forEach((data: { roles: any }) => {
      this.links = data.roles.links;
    });

    this.searchBox
      .control
      .valueChanges
      .debounceTime(300)
      .subscribe((event) => {
        this.filter = QueryBuilder.like(event, "name");
      });

  }

  roles: Items<RoleRow> = (query, response) => {
    query.predicate = this.filter;
    this.http.post('service/usercontrol/role/table', query)
      .subscribe((res: Response) => {
        const json = res.json();
        response(json.rows, json.size);
      });
  };


  onSelection(role: RoleRow) {
    if (role.links.find((link : Link) => link.rel === "read")) {
      this.router.navigate(['usercontrol/role', role.id]);
    }
  }


  onCreate() {
    this.router.navigate(['usercontrol/role']);
  }


}
