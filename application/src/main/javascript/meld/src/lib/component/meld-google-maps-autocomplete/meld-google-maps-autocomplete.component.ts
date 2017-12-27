import {Component, forwardRef, Input} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {Place} from './meld-google-maps-autocomplete.interfaces';
import {Container} from '../../common/rest/Container';
import {Selects} from '../meld-combobox/meld-combobox.interfaces';
import {Objects} from '../../common/utils/Objects';

const noop = () => {
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldGoogleMapsAutocompleteComponent),
  multi: true
};


@Component({
  selector: 'meld-google-maps-autocomplete',
  templateUrl: 'meld-google-maps-autocomplete.component.html',
  styleUrls: ['meld-google-maps-autocomplete.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class MeldGoogleMapsAutocompleteComponent implements ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  public value: Place;

  @Input('name')
  public name: string;

  @Input('placeholder')
  public placeholder: string;

  @Input('firstPartOnly')
  public firstPartOnly: boolean = false;

  constructor(private http: HttpClient) {
  }

  places: Selects<Place> = (search, callback) => {
    this.http.post<Container<Place>>('service/generic/google/place/autocomplete', {name: search.filter})
      .subscribe((res) => {
        callback(res.rows, res.rows.length);
      });
  };

  process(value: string): string {
    if (this.firstPartOnly) {
      let parts = value.split(',');
      return parts[0];
    }
    return value;
  }

  onValueChange(event: any) {
    if (Objects.isNotNull(event)) {
      this.http.get<Place>(`service/generic/google/place/${event.id}/details`)
        .subscribe((res) => {
          this.value = res;
          this.onChangeCallback(this.value);
        });
    }
  }

  public itemValue = (item) => {
    if (item == null) {
      return null;
    }
    return item;
  };

  public itemName = (item) => {
    if (item == null) {
      return null;
    }
    let name : string = item['name'];
    return this.process(name);
  };

  writeValue(obj: any): void {
    if (Objects.isNotNull(obj)) {
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
