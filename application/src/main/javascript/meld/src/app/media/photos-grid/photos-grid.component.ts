import { Component, OnInit } from '@angular/core';
import {Items} from '../../../lib/common/query/Items';

@Component({
  selector: 'app-photos-grid',
  templateUrl: 'photos-grid.component.html',
  styleUrls: ['photos-grid.component.css']
})
export class PhotosGridComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  photos : Items<any> = (query, callback) => {
    const array = [];

    for (var i = query.index; i < query.index + query.limit; i++) {

      array.push({id : i, name : 'test' + i})
    }

    callback(array, 1000);
  }

}
