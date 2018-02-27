import {Component, forwardRef, ViewEncapsulation} from '@angular/core';
import {Selects} from '../../../../../lib/component/meld-combobox/meld-combobox.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {CompanyItem} from './company-select.interfaces';
import {AbstractSelect} from '../../../../../lib/common/forms/AbstractSelect';
import {HttpClient} from '@angular/common/http';
import {NG_VALUE_ACCESSOR} from '@angular/forms';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => CompanySelectComponent),
  multi: true
};

@Component({
  selector: 'app-social-company-select',
  templateUrl: 'company-select.component.html',
  styleUrls: ['company-select.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class CompanySelectComponent extends AbstractSelect<CompanyItem> {

  constructor(private http: HttpClient) {
    super();
  }

  items: Selects<CompanyItem> = (search, callback) => {
    let params = {
      index: search.index.toString(),
      count: search.limit.toString(),
      sort: 'name:asc',
      name: search.filter
    };

    this.http.get('service/social/user/current/work/history/companies', {params: params})
      .subscribe((response: Container<CompanyItem>) => {
        callback(response.rows, response.size);
      });
  };

}
