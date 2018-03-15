import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NgModel} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {GroupRow} from './group-table.interfaces';
import {Container, Items, Link, MeldTableComponent} from '@aestheticcode/meld-lib';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-group-table',
  templateUrl: 'group-table.component.html',
  styleUrls: ['group-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class GroupTableComponent implements OnInit {

  filter: string;

  links: Link[] = [];

  @ViewChild('searchBox')
  searchBox: NgModel;

  @ViewChild('grid')
  grid: MeldTableComponent;

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
      index: query.index.toString(),
      limit: query.limit.toString(),
      sort: query.sort
    };

    if (this.filter) {
      params['name'] = this.filter;
    }

    this.http.get<Container<GroupRow>>('service/usercontrol/group/table', {params: params})
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
