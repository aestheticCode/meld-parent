import {Component, forwardRef, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {Selects} from '../../../../../lib/component/meld-combobox/meld-combobox.interfaces';
import {AddressItem} from './address-select.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';

const noop = () => {};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => AddressSelectComponent),
  multi: true
};


@Component({
  selector: 'app-social-address-select',
  templateUrl: 'address-select.component.html',
  styleUrls: ['address-select.component.css'],
  encapsulation : ViewEncapsulation.None,
  providers : [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class AddressSelectComponent implements ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  public value : string;

  @Input("placeholder")
  public placeholder : string;

  constructor(private http : HttpClient) { }

  addresses : Selects<AddressItem> = (search, callback) => {
    let params = {
      index : search.index.toString(),
      count : search.limit.toString(),
      sort : 'place.name:asc',
      name : search.filter
    };

    this.http.get("service/social/user/current/places/addresses", {params : params})
      .subscribe((response : Container<AddressItem>) => {
          callback(response.rows, response.size);
      });
  };

  onModelChange(value : string) {
    this.onChangeCallback(value);
  }

  writeValue(obj: any): void {
    if (obj) {
      this.value = obj;
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }



}
