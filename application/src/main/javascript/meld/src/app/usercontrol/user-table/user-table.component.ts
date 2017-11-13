import {Component, Input, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {Predicate} from "../../../lib/common/predicates/Predicate";
import {MeldTableComponent} from "../../../lib/component/meld-table/meld-table.component";
import {Http, Response} from "@angular/http";
import {Strings} from "../../../lib/common/utils/Strings";
import {Objects} from "../../../lib/common/utils/Objects";
import {QueryBuilder} from "../../../lib/common/query/QueryBuilder";
import {Items} from "../../../lib/common/query/Items";
import {Container} from "../../../lib/common/rest/Container";
import {UserRow} from "./UserRow";
import {FilterTemplate} from "./FilterTemplate";
import {FilterTemplateModel} from "./FilterTemplateModel";
import {NgModel} from "@angular/forms";
import {Link} from "../../../lib/common/rest/Link";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-user-table',
  templateUrl: 'user-table.component.html',
  styleUrls: ['user-table.component.css']
})
export class UserTableComponent implements OnInit {

  @Input("filter")
  public filter: Predicate<any>;

  public filterTemplate: FilterTemplate = new FilterTemplateModel();

  @ViewChild("table")
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
      .subscribe((event : string) => {
        this.filter = QueryBuilder.or([
          QueryBuilder.like(event, "name"),
          QueryBuilder.like(event, "firstName"),
          QueryBuilder.like(event, "lastName"),
        ]);
      });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filter']) {
      this.table.refreshItems();
    }
  }

  onCancelClick() {
    this.filterTemplate = new FilterTemplateModel();
    this.filter = undefined;
    this.table.refreshItems();
  }

  onSearchClick() {
    const result: Predicate<any>[] = [];

    if (Strings.isNotEmpty(this.filterTemplate.name)) {
      result.push(QueryBuilder.like(this.filterTemplate.name, "name"))
    }

    if (Strings.isNotEmpty(this.filterTemplate.firstName)) {
      result.push(QueryBuilder.like(this.filterTemplate.firstName, "firstName"))
    }

    if (Strings.isNotEmpty(this.filterTemplate.lastName)) {
      result.push(QueryBuilder.like(this.filterTemplate.lastName, "lastName"))
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
    query.predicate = this.filter;
    this.http.post('service/usercontrol/user/table', query)
      .subscribe((res: Response) => {
        const json = res.json() as Container<UserRow>;
        response(json.rows, json.size);
      });
  };

  onSelectionChange(user: UserRow) {
    if (user.links.find((link) => link.rel === "read")) {
      this.router.navigate(['usercontrol/user', user.id]);
    }
  }

  onCreate() {
    this.router.navigate(['usercontrol/user']);
  }




}
