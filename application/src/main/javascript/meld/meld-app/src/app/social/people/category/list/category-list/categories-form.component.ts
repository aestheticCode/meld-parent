import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Container, ContainerModel} from '@aestheticcode/meld-lib';
import {Category} from '../../categories.interfaces';

@Component({
  selector: 'app-categories-form',
  templateUrl: 'categories-form.component.html',
  styleUrls: ['categories-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CategoriesFormComponent implements OnInit {

  container: Container<Category>;

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.data.forEach((data: { container: Container<Category> }) => {
      this.container = data.container || new ContainerModel<Category>();
    });
  }


  onCreate() {
    this.router.navigate(['social', 'people', {outlets: {people: ['category']}}]);
  }

  onEdit(event: MouseEvent, category: Category) {
    event.stopPropagation();
    this.router.navigate(['social', 'people', {outlets: {people: ['category', category.id]}}]);
    return false;
  }

}
