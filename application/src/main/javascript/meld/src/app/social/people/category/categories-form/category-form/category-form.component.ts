import {Component, OnInit} from '@angular/core';
import {Category} from '../../categories.interfaces';
import {HttpClient} from '@angular/common/http';
import {UserForm} from '../../../../../usercontrol/user/form/user.interfaces';
import {UserFormModel} from '../../../../../usercontrol/user/form/user.classes';
import {ActivatedRoute, Router} from '@angular/router';
import {CategoryModel} from '../../categories.classes';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent implements OnInit {

  category: Category;

  constructor(private route : ActivatedRoute,
              private router : Router,
              private http: HttpClient) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { category: Category }) => {
      this.category = data.category || new CategoryModel();
    });

  }

  onCreate() {
    this.http.post<Category>('service/social/people/category', this.category)
      .subscribe((res: Category) => {
        this.router.navigate(['social', 'people', { outlets: { people: ['categories'] } }]);
      });
  }

  onDelete() {
    this.http.delete(`service/social/people/category/${this.category.id}`)
      .subscribe((res) => {
        this.router.navigate(['social', 'people', { outlets: { people: ['categories'] } }]);
      });
  }


  onRename() {
    this.http.put<Category>(`service/social/people/category/${this.category.id}`, this.category)
      .subscribe((res: Category) => {
        this.router.navigate(['social', 'people', { outlets: { people: ['categories'] } }]);
      });
  }

  onCancel() {
    this.router.navigate(['social', 'people', { outlets: { people: ['categories'] } }]);
  }


}
