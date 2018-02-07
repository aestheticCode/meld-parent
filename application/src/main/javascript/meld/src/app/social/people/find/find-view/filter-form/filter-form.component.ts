import {Component, Input, ViewEncapsulation} from '@angular/core';
import {Filter} from '../../find.interfaces';

@Component({
  selector: 'app-social-find-filter-form',
  templateUrl: 'filter-form.component.html',
  styleUrls: ['filter-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class FilterFormComponent {

  @Input("filter")
  public filter: Filter;

}
