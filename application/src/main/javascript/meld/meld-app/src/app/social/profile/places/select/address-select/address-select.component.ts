import {Component, forwardRef, ViewEncapsulation} from '@angular/core';
import {NG_VALUE_ACCESSOR} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {AddressItem} from './address-select.interfaces';
import {AbstractSelect} from '@aestheticcode/meld-lib';
import {Selects} from '@aestheticcode/meld-lib';
import {Container} from '@aestheticcode/meld-lib';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => AddressSelectComponent),
  multi: true
};

@Component({
  selector: 'app-social-address-select',
  templateUrl: 'address-select.component.html',
  styleUrls: ['address-select.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class AddressSelectComponent extends AbstractSelect<AddressItem> {

  constructor(private http: HttpClient) {
    super();
  }

  items: Selects<AddressItem> = (search, callback) => {
    let params = {
      index: search.index.toString(),
      count: search.limit.toString(),
      sort: 'place.name:asc',
      name: search.filter
    };

    this.http.get('service/social/user/current/places/addresses', {params: params})
      .subscribe((response: Container<AddressItem>) => {
        callback(response.rows, response.size);
      });
  };

}
