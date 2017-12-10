import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {Http, Response} from "@angular/http";
import {Items} from '../../../lib/common/query/Items';
import {Item} from './meld-item/meld-item.interfaces';
import {QueryBuilder} from '../../../lib/common/query/QueryBuilder';
import {AppService} from '../../app.service';

@Component({
  selector: 'app-meld-list',
  templateUrl: 'meld-list.component.html',
  styleUrls: ['meld-list.component.css']
})
export class MeldListComponent {

  constructor(private http : Http,
              private service : AppService,
              private router: Router) {}

  posts: Items<Item> = (query, callback) => {
    let equal = QueryBuilder.equal(this.service.configuration.user.id, "from.id");
    query.predicate = QueryBuilder.subQuery("user", "user", "relationShip", "to", equal);

    this.http.post('service/channel/meld/posts/', query)
      .subscribe((res: Response) => {
        let rows: [any] = res.json().rows;
        callback(rows, null);
      });
  };

  onCreate() {
    this.router.navigate(['channel/meld/post'])
  }

  onPostClick(post) {
    this.router.navigate(['channel/meld/post', post.id])
  }

}
