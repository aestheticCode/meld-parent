import {Component, EventEmitter, forwardRef, Input, OnInit, Output, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ControlValueAccessor, NG_VALUE_ACCESSOR, NgModel} from '@angular/forms';
import {MatAutocompleteSelectedEvent} from '@angular/material';
import {Strings} from '../../common/utils/Strings';

const noop = () => {};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldGoogleMapsAutocompleteComponent),
  multi: true
};


@Component({
  selector: 'meld-google-maps-autocomplete',
  templateUrl: 'meld-google-maps-autocomplete.component.html',
  styleUrls: ['meld-google-maps-autocomplete.component.css'],
  providers : [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class MeldGoogleMapsAutocompleteComponent implements OnInit, ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  public value : string;

  public locations = [];

  @ViewChild('input')
  private input : NgModel;

  @Output('optionsSelected')
  private optionSelected: EventEmitter<MatAutocompleteSelectedEvent> = new EventEmitter<MatAutocompleteSelectedEvent>();

  @Input('name')
  public name : string;

  @Input('placeholder')
  public placeholder : string;

  constructor(private http : HttpClient) { }

  ngOnInit() {
    this
      .input
      .control
      .valueChanges
      .debounceTime(300)
      .subscribe((value: string) => {
        this.http.post<any>("service/generic/google/place", {value: value})
          .subscribe((res) => {
            this.locations = res.rows;
          })
      })
  }

  onOptionSelected(event) {
    this.optionSelected.emit(event);
  }

  process(value : string) : string {
    let parts = value.split(",");
    return parts[0];
  }

  onValueChange(event : string) {
    this.onChangeCallback(event);
  }

  writeValue(obj: any): void {
    if (Strings.isNotEmpty(obj)) {
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
