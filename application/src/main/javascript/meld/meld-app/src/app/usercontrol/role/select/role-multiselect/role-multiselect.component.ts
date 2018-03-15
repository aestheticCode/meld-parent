import {Component, forwardRef, Input, OnChanges, SimpleChanges, ViewEncapsulation} from '@angular/core';
import {Http, Response} from '@angular/http';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {RoleSelect} from './role-multiselect.interfaces';
import {Selects} from '@aestheticcode/meld-lib';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Container} from '@aestheticcode/meld-lib';

const noop = () => {
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => RoleMultiselectComponent),
  multi: true
};

@Component({
  selector: 'app-role-multiselect',
  templateUrl: 'role-multiselect.component.html',
  styleUrls: ['role-multiselect.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
  encapsulation: ViewEncapsulation.None
})
export class RoleMultiselectComponent implements ControlValueAccessor, OnChanges {

  public value: any[];

  @Input('placeholder')
  public placeholder: string;

  @Input('readonly')
  public readonly: boolean = false;

  @Input('disabled')
  public disabled: boolean = false;

  @Input('hideComboBox')
  public hideComboBox: boolean = false;

  private onTouchedCallback: () => void = noop;

  private onChangeCallback: (value: any) => void = noop;

  constructor(private http: HttpClient) {
  }

  roles: Selects<RoleSelect> = (query, response) => {

    const params = {
      index: query.index.toString(),
      limit: query.limit.toString(),
      name: query.filter
    };

    this.http.get<Container<RoleSelect>>('service/usercontrol/role/multiselect', {params : params})
      .subscribe((res) => {
        response(res.rows, res.size);
      });
  };

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['value']) {
      this.onChangeCallback(this.value);
    }
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
