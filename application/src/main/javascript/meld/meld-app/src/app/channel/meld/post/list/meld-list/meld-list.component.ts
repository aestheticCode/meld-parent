import {Component, ViewEncapsulation} from '@angular/core';
import {Item} from './meld-item/meld-item.interfaces';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from '@aestheticcode/meld-lib';
import {AppService} from '../../../../../app.service';
import {Items} from '@aestheticcode/meld-lib';
import {Container} from '@aestheticcode/meld-lib';

@Component({
  selector: 'app-meld-list',
  templateUrl: 'meld-list.component.html',
  styleUrls: ['meld-list.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MeldListComponent {

  constructor(private http: HttpClient,
              private service: AppService,
              private router: MeldRouterService) {
  }

  posts: Items<Item> = (query, callback) => {

    const params = {
      index: query.index.toString(),
      limit: query.limit.toString(),
      sort: 'created:desc'
    };

    if (this.router.queryParam.home) {
      params['home'] = 'true';
    }

    if (this.router.queryParam.profile) {
      params['profile'] = this.router.queryParam.id;
    }

    this.http.get<Container<Item>>('service/channel/meld/posts/', {params: params})
      .subscribe((res: Container<Item>) => {
        callback(res.rows, null);
      });
  };


  onCreate() {
    this.router.navigate(['channel/meld/post/text']);
  }

  onPostClick(post) {
    this.router.navigate(['channel/meld/post', post.id, post.type]);
  }

}
