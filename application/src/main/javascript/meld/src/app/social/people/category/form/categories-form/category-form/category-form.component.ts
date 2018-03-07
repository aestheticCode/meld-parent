import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Category} from '../../../categories.interfaces';
import {HttpClient} from '@angular/common/http';
import {AbstractForm} from '../../../../../../../lib/common/forms/AbstractForm';
import {Observable} from 'rxjs/Observable';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-category-form',
  templateUrl: 'category-form.component.html',
  styleUrls: ['category-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CategoryFormComponent extends AbstractForm<Category> implements OnInit {

  category: Category;

  constructor(private router: MeldRouterService,
              private http: HttpClient) {
    super();
  }

  ngOnInit() {
    this.category = this.router.data.category;
  }

  onCancel() {
    this.router.navigate(['social', 'people', {outlets: {people: ['categories']}}]);
  }

  saveRequest(): Observable<Category> {
    return this.http.post<Category>('service/social/people/category', this.category);
  }

  updateRequest(): Observable<Category> {
    return this.http.put<Category>(`service/social/people/category/${this.category.id}`, this.category);

  }

  deleteRequest(): Observable<Category> {
    return this.http.delete<Category>(`service/social/people/category/${this.category.id}`);
  }


  public postRequest(form: Category) {
    this.router.navigate(['social', 'people', {outlets: {people: ['categories']}}]);
  }
}
