import {Component, forwardRef, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Selects} from 'lib/component/meld-combobox/meld-combobox.interfaces';
import {Container} from 'lib/common/rest/Container';
import {NG_VALUE_ACCESSOR} from '@angular/forms';
import {AbstractSelect} from '../../../../../../lib/common/forms/AbstractSelect';
import {School} from '../../school-form.interfaces';

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

    const params = {
      index: search.index.toString(),
      limit: search.limit.toString(),
      name: search.filter,
      sort: 'name:asc'
    };

    this.http.get<Container<School>>('service/social/education/find', {params: params})
      .subscribe((schools: Container<School>) => {
        response(schools.rows, schools.size);
      });
  };


}
