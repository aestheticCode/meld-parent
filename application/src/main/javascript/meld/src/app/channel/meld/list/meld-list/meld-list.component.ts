import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Http, Response} from '@angular/http';
import {Items} from '../../../../../lib/common/query/Items';
import {Item} from './meld-item/meld-item.interfaces';
import {AppService} from '../../../../app.service';
import {QueryBuilder} from '../../../../../lib/common/query/QueryBuilder';

@Component({
  selector: 'app-meld-list',
  templateUrl: 'meld-list.component.html',
  styleUrls: ['meld-list.component.css']
})
export class MeldListComponent {

  constructor(private http: Http,
              private service: AppService,
              private router: Router) {
  }

  posts: Items<Item> = (query, callback) => {
    let subQueryPredicate = this.subQueryForRelations(this.service.configuration.user.id);
    let equalPredicate = this.equalForCurrentUser(this.service.configuration.user.id);

    query.predicate = QueryBuilder.or([
      subQueryPredicate,
      equalPredicate
    ]);

    this.http.post('service/channel/meld/posts/', query)
      .subscribe((res: Response) => {
        let rows: [any] = res.json().rows;
        callback(rows, null);
      });
  };

  private equalForCurrentUser(id: string) {
    return QueryBuilder.equal(id, 'user.id');
  }

  private subQueryForRelations(id: string) {
    return QueryBuilder.and([
      QueryBuilder.or([
        QueryBuilder.isNull("category"),
        QueryBuilder.inSelect(
          "category",
          QueryBuilder.subQuery(
            "relationShip",
            "category",
            QueryBuilder.equal(id, "to.id")
          )
        )
      ]),
      QueryBuilder.inSelect(
        'user',
        QueryBuilder.subQuery(
          'relationShip',
          'to',
          QueryBuilder.equal(id, 'from.id')
        ))
    ]);
  }

  onCreate() {
    this.router.navigate(['channel/meld/post']);
  }

  onPostClick(post) {
    this.router.navigate(['channel/meld/post', post.id]);
  }

}
