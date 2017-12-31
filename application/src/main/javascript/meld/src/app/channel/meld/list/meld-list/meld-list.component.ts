import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Http, Response} from '@angular/http';
import {Item} from './meld-item/meld-item.interfaces';
import {AppService} from '../../../../app.service';
import {NormalSort, QueryBuilder} from '../../../../../lib/common/search/search.classes';
import {Items} from '../../../../../lib/common/search/search.interfaces';

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

    query.expression = QueryBuilder.or([
      subQueryPredicate,
      equalPredicate
    ]);

    query.sorting = [new NormalSort("created", false)];

    this.http.post('service/channel/meld/posts/', query)
      .subscribe((res: Response) => {
        let rows: [any] = res.json().rows;
        callback(rows, null);
      });
  };

  private equalForCurrentUser(id: string) {
    return QueryBuilder.path('user.id', QueryBuilder.equal(id));
  }

  private subQueryForRelations(id: string) {
    return QueryBuilder.and([
      QueryBuilder.or([
        QueryBuilder.path('category', QueryBuilder.isNull()),
        QueryBuilder.path('category', QueryBuilder.inSelect(
          QueryBuilder.subQuery(
            'relationShip',
            'category',
            QueryBuilder.path('to.id', QueryBuilder.equal(id))
          )
        ))
      ]),
      QueryBuilder.path('user', QueryBuilder.inSelect(
        QueryBuilder.subQuery(
          'relationShip',
          'to',
          QueryBuilder.path('from.id', QueryBuilder.equal(id))
        )))
    ]);
  }

  onCreate() {
    this.router.navigate(['channel/meld/post/text']);
  }

  onPostClick(post) {
    this.router.navigate(['channel/meld/post', post.id, post.type]);
  }

}
