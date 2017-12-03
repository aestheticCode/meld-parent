import {Component, Input} from '@angular/core';
import {Category} from "../categories-form.interfaces";
import {HttpClient} from "@angular/common/http";
import {Items} from "../../../../../lib/common/query/Items";
import {UserRow} from "./category.interfaces";
import {Container} from "../../../../../lib/common/rest/Container";
import {QueryBuilder} from "../../../../../lib/common/query/QueryBuilder";
import {MatDialog} from "@angular/material";

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
    query.predicate = QueryBuilder.equal(this.category.id, "category");
    this.http.post<Container<UserRow>>('service/social/people/find', query)
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };


}
