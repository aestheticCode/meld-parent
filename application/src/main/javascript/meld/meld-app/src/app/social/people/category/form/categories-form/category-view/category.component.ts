import {Component, Input, ViewEncapsulation} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserRow} from "./category.interfaces";
import {MatDialog} from "@angular/material";
import {Category} from '../../../categories.interfaces';
import {Container} from '@aestheticcode/meld-lib';
import {Items} from '@aestheticcode/meld-lib';

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
    this.http.get<Container<UserRow>>('service/social/people/find', {params : {category : this.category.id }})
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };


}
