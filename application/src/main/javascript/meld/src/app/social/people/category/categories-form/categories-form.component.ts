import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {CategoryCreateDialogComponent} from "./category-create-dialog/category-dialog.component";
import {MatDialog} from "@angular/material";
import {CategoryDeleteDialogComponent} from "./category-delete-dialog/category-delete-dialog.component";
import {CategoryRenameDialogComponent} from "./category-rename-dialog/category-rename-dialog.component";
import {Container, ContainerModel} from '../../../../../lib/common/rest/Container';
import {Category} from '../categories.interfaces';
import {Strings} from '../../../../../lib/common/utils/Strings';

@Component({
  selector: 'app-categories-form',
  templateUrl: 'categories-form.component.html',
  styleUrls: ['categories-form.component.css']
})
export class CategoriesFormComponent implements OnInit {

  container : Container<Category>;

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private router : Router,
              private dialog : MatDialog) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { container: Container<Category> }) => {
      this.container = data.container || new ContainerModel<Category>();
    });
  }


  create() {

    let dialogRef = this.dialog.open(CategoryCreateDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      if (Strings.isNotEmpty(result)) {
        this.http.post<Category>("service/social/people/category", {name : result})
          .subscribe((res : Category) => this.container.rows.push(res))
      }
    })

  }

  onDelete(event : MouseEvent, category : Category) {
    event.stopPropagation();
    let dialogRef = this.dialog.open(CategoryDeleteDialogComponent);
    dialogRef.afterClosed().subscribe((result : boolean) => {
      this.http.delete(`service/social/people/category/${category.id}`)
        .subscribe((res) => {});
    });
    return false;
  }


  onRename(event : MouseEvent, category : Category) {
    event.stopPropagation();
    let dialogRef = this.dialog.open(CategoryRenameDialogComponent);
    dialogRef.afterClosed().subscribe((result : string) => {
      if (Strings.isNotEmpty(result)) {
        this.http.put<Category>(`service/social/people/category/${category.id}`, {name : result})
          .subscribe((res : Category) => {
            this.container.rows.forEach((row, index) => {
              if (row.id === res.id) {
                this.container.rows[index] = res;
              }
            });
          });
      }
    });
    return false;
  }
}
