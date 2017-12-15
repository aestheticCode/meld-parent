import {Component, Input} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserRow} from "./category.interfaces";
import {MatDialog} from "@angular/material";
import {Category} from '../../categories.interfaces';
import {Items} from '../../../../../../lib/common/query/Items';
import {QueryBuilder} from '../../../../../../lib/common/query/QueryBuilder';
import {Container} from '../../../../../../lib/common/rest/Container';

@Component({
  selector: 'app-social-category',
  templateUrl: 'category.component.html',
  styleUrls: ['category.component.css']
})
export class CategoryComponent {

  @Input("category")
  category: Category;


  constructor(private http: HttpClient,
              private dialog : MatDialog) {}

  users: Items<UserRow> = (query, response) => {
    query.predicate = QueryBuilder.inSelect(
      "",
      QueryBuilder.subQuery(
        "relationShip",
        "to", QueryBuilder.equal(this.category.id, "category.id"))
    );
    this.http.post<Container<UserRow>>('service/social/people/find', query)
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };


}
