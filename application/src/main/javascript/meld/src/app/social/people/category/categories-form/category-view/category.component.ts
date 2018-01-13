import {Component, Input, ViewEncapsulation} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserRow} from "./category.interfaces";
import {MatDialog} from "@angular/material";
import {Category} from '../../categories.interfaces';
import {Container} from '../../../../../../lib/common/rest/Container';
import {QueryBuilder} from '../../../../../../lib/common/search/search.classes';
import {Items} from '../../../../../../lib/common/search/search.interfaces';

@Component({
  selector: 'app-social-category',
  templateUrl: 'category.component.html',
  styleUrls: ['category.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CategoryComponent {

  @Input("category")
  category: Category;


  constructor(private http: HttpClient) {}

  users: Items<UserRow> = (query, response) => {
    query.expression = QueryBuilder.inSelect(
      QueryBuilder.subQuery(
        "relationShip",
        "to",
        QueryBuilder.path("category.id", QueryBuilder.equal(this.category.id)))
    );
    this.http.post<Container<UserRow>>('service/social/people/find', query)
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };


}
