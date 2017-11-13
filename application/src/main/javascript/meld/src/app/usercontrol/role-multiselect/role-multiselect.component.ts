import {Component, forwardRef, Input, OnChanges, SimpleChanges} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {Items} from "../../../lib/common/query/Items";
import {RoleSelect} from "./RoleSelect";

const noop = () => {};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => RoleMultiselectComponent),
  multi: true
};


@Component({
  selector: 'app-role-multiselect',
  templateUrl: 'role-multiselect.component.html',
  styleUrls: ['role-multiselect.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class RoleMultiselectComponent implements ControlValueAccessor, OnChanges {

  public value: any[];

  @Input("placeholder")
  public placeholder: string;

  private onTouchedCallback: () => void = noop;

  private onChangeCallback: (value: any) => void = noop;

  constructor(private http: Http) {}

  roles: Items<RoleSelect> = (query, response) => {
    this.http.post('service/usercontrol/role/multiselect', query)
      .subscribe((res: Response) => {
        const json = res.json();
        response(json.rows, json.size);
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
