import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Container, Items} from '@aestheticcode/meld-lib';
import {Photo} from '../../../../../media/photo/grid/grid.interfaces';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-social-find-view',
  templateUrl: 'find-view.component.html',
  styleUrls: ['find-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class FindViewComponent implements OnInit {

  constructor(private http : HttpClient) { }

  ngOnInit() {
  }

  communities: Items<Photo> = (query, callback) => {

    const params = {
      index : query.index.toString(),
      limit : "" + 75
    };

    this.http.get<Container<Photo>>('service/social/communities/grid', {params : params})
      .subscribe((res) => {
        callback(res.rows, res.size);
      });
  };

  create() {

  }

}
