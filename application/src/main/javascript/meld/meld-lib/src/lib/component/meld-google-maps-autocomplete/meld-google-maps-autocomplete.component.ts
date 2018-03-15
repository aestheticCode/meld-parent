import {Component, forwardRef, HostBinding, Input, Optional, Self, ViewChild, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ControlValueAccessor, NG_VALUE_ACCESSOR, NgControl} from '@angular/forms';
import {Place} from './meld-google-maps-autocomplete.interfaces';
import {Container} from '../../common/rest/Container';
import {Selects} from '../meld-combobox/meld-combobox.interfaces';
import {Objects} from '../../common/utils/Objects';
import {AbstractControl} from '../../common/forms/AbstractControl';
import {MeldComboBoxComponent} from '../meld-combobox/meld-combobox.component';
import {MatFormFieldControl} from '@angular/material';

const noop = () => {
};

@Component({
  selector: 'meld-google-maps-autocomplete',
  templateUrl: 'meld-google-maps-autocomplete.component.html',
  styleUrls: ['meld-google-maps-autocomplete.component.css'],
  providers: [{provide: MatFormFieldControl, useExisting: MeldGoogleMapsAutocompleteComponent}],
  encapsulation : ViewEncapsulation.None
})
export class MeldGoogleMapsAutocompleteComponent extends AbstractControl<Place> implements ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  static nextId = 0;

  @HostBinding()
  id = `meld-google-maps-autocomplete-${MeldComboBoxComponent.nextId++}`;

  get focused() {
    return this.comboBox.focused;
  }

  onContainerClick(event: MouseEvent): void {
    this.comboBox.focus();
  }

  get empty() {
    return Objects.isNull(this.value);
  }

  public value: Place;

  @Input('firstPartOnly')
  public firstPartOnly: boolean = false;

  @Input("name")
  public name : string;

  @ViewChild("comboBox")
  private comboBox : MeldComboBoxComponent;

  constructor(private http: HttpClient,
              @Optional() @Self() public ngControl: NgControl) {
    super(ngControl);
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
