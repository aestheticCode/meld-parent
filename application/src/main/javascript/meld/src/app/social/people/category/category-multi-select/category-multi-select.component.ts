import {Component, forwardRef, ViewEncapsulation} from '@angular/core';
import {Selects} from '../../../../../lib/component/meld-combobox/meld-combobox.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {Category} from '../categories.interfaces';
import {AbstractSelect} from '../../../../../lib/common/forms/AbstractSelect';
import {HttpClient} from '@angular/common/http';
import {NG_VALUE_ACCESSOR} from '@angular/forms';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => CategoryMultiSelectComponent),
  multi: true
};

@Component({
  selector: 'app-social-category-multi-select',
  templateUrl: 'category-multi-select.component.html',
  styleUrls: ['category-multi-select.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers : [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]

})
export class CategoryMultiSelectComponent extends AbstractSelect<Category> {

  constructor(private http: HttpClient) {
    super();
  }

  items: Selects<Category> = (search, response) => {

    const params = {
      index: search.index.toString(),
      limit: search.limit.toString()
    };

    this.http.get<Container<Category>>('service/social/people/categories', {params: params})
      .subscribe((res: Container<Category>) => {
        response(res.rows, res.size);
      });
  };


}