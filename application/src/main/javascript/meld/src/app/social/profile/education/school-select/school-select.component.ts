import {Component, forwardRef, ViewEncapsulation} from '@angular/core';
import {School} from '../school-form.interfaces';
import {HttpClient} from '@angular/common/http';
import {Selects} from 'lib/component/meld-combobox/meld-combobox.interfaces';
import {QueryBuilder} from 'lib/common/search/search.classes';
import {Container} from 'lib/common/rest/Container';
import {NG_VALUE_ACCESSOR} from '@angular/forms';
import {AbstractSelect} from '../../../../../lib/common/forms/AbstractSelect';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SchoolSelectComponent),
  multi: true
};

@Component({
  selector: 'app-social-school-select',
  templateUrl: 'school-select.component.html',
  styleUrls: ['school-select.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class SchoolSelectComponent extends AbstractSelect<School> {

  constructor(protected http: HttpClient) {
    super();
  }

  items: Selects<School> = (search, response) => {

    let query = QueryBuilder.query();
    query.index = search.index;
    query.limit = search.limit;
    query.expression = QueryBuilder.path('name', QueryBuilder.like(search.filter));

    this.http.post<Container<School>>('service/social/education/find', query)
      .subscribe((schools: Container<School>) => {
        response(schools.rows, schools.size);
      });
  };


}
