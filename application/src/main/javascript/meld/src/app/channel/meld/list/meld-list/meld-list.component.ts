import {Component, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {Http, Response} from '@angular/http';
import {Item} from './meld-item/meld-item.interfaces';
import {AppService} from '../../../../app.service';
import {NormalSort, QueryBuilder} from '../../../../../lib/common/search/search.classes';
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {MeldChannel} from './meld-list.interfaces';
import {Selects} from '../../../../../lib/component/meld-combobox/meld-combobox.interfaces';
import {HttpClient} from '@angular/common/http';
import {Container} from '../../../../../lib/common/rest/Container';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';
import {RestExpression} from '../../../../../lib/common/search/expression.interfaces';

@Component({
  selector: 'app-meld-list',
  templateUrl: 'meld-list.component.html',
  styleUrls: ['meld-list.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldListComponent {

  private expression : RestExpression;

  constructor(private http: HttpClient,
              private service: AppService,
              private router: MeldRouterService) {

    if (router.queryParam.home) {
      let subQueryPredicate = this.subQueryForRelations(this.service.configuration.user.id);
      let equalPredicate = this.equalForUser(this.service.configuration.user.id);

      this.expression = QueryBuilder.or([
        subQueryPredicate,
        equalPredicate
      ]);
    }

    if (router.queryParam.profile) {
      this.expression = this.equalForUser(router.queryParam.id);
    }

  }

  posts: Items<Item> = (query, callback) => {
    query.expression = this.expression;
    query.sorting = [new NormalSort("created", false)];
    this.http.post<Container<Item>>('service/channel/meld/posts/', query)
      .subscribe((res: Container<Item>) => {
        callback(res.rows, null);
      });
  };

  private equalForUser(id: string) {
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
