import {Component} from '@angular/core';
import {Category, UserRow} from "./find-view.interfaces";
import {Items} from "../../../../lib/common/query/Items";
import {Container} from "../../../../lib/common/rest/Container";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-social-find-view',
  templateUrl: 'find-view.component.html',
  styleUrls: ['find-view.component.css']
})
export class FindViewComponent {

  constructor(private http: HttpClient) {}

  users: Items<UserRow> = (query, response) => {
    this.http.post<Container<UserRow>>('service/social/people/find', query)
      .subscribe((res: Container<UserRow>) => {
        response(res.rows, res.size);
      });
  };

  categories: Items<Category> = (query, response) => {
    this.http.post<Container<Category>>('service/social/people/categories', query)
      .subscribe((res: Container<Category>) => {
        response(res.rows, res.size);
      });
  };

  onUpdate(id : string, category : Category) {
    this.http.put<Category>(`service/social/people/find/user/${id}`, category || {})
      .subscribe((res : Category) => {})
  }


}
