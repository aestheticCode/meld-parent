import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NgModel} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Link} from '../../../../../lib/common/rest/Link';
import {RoleRow} from './role-table.intefaces';
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {HttpClient} from '@angular/common/http';
import {Container} from '../../../../../lib/common/rest/Container';
import {MeldTableComponent} from '../../../../../lib/component/meld-table/meld-table.component';

@Component({
  selector: 'app-role-table',
  templateUrl: 'role-table.component.html',
  styleUrls: ['role-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RoleTableComponent implements OnInit {

  public filter: string;

  public links: Link[] = [];

  @ViewChild('searchBox')
  public searchBox: NgModel;

  @ViewChild("grid")
  public grid : MeldTableComponent

  constructor(private http: HttpClient,
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
        this.filter = event;
        this.grid.refreshItems();
      });

  }

  roles: Items<RoleRow> = (query, response) => {

    const params = {
      index: query.index.toString(),
      limit: query.limit.toString(),
      name: this.filter,
      sort: query.sort
    };

    this.http.get<Container<RoleRow>>('service/usercontrol/role/table', {params: params})
      .subscribe((res) => {
        response(res.rows, res.size);
      });
  };


  onSelection(role: RoleRow) {
    if (role.links.find((link: Link) => link.rel === 'read')) {
      this.router.navigate(['usercontrol/role', role.id]);
    }
  }


  onCreate() {
    this.router.navigate(['usercontrol/role']);
  }


}
