import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {Items} from "../../../lib/component/meld-list/Items";
import {Http, Response} from "@angular/http";

@Component({
  selector: 'app-meld-list',
  templateUrl: 'meld-list.component.html',
  styleUrls: ['meld-list.component.css']
})
export class MeldListComponent {

  constructor(private http : Http,
              private router: Router) {}

  posts: Items = (params, callback) => {
    this.http.post('service/channel/meld/posts/', {start: params.index, limit: params.count})
      .subscribe((res: Response) => {
        let rows: [any] = res.json().rows;
        callback(rows);
      });
  };

  onCreate() {
    this.router.navigate(['channel/meld/post'])
  }

  onPostClick(post) {
    this.router.navigate(['channel/meld/post', post.id])
  }

}
