import {Component, forwardRef, Input} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {Strings} from "../../common/utils/Strings";

const noop = () => {};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldFormGroupComponent),
  multi: true
};

@Component({
  selector: 'meld-form-group',
  templateUrl: 'meld-form-group.component.html',
  styleUrls: ['meld-form-group.component.css'],
  providers : [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class MeldFormGroupComponent implements ControlValueAccessor {

  private onTouchedCallback: () => void = noop;

  private onChangeCallback: (value: any) => void = noop;

  public value: string;

  @Input("name")
  public name: string;

  @Input("label")
  public label : string;

  writeValue(obj: any): void {
    this.value = obj;
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

}
