import {ControlValueAccessor} from '@angular/forms';
import {Selects} from '../../component/meld-combobox/meld-combobox.interfaces';
import {Input} from '@angular/core';

const noop = () => {
};

export abstract class AbstractSelect<S> implements ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  public value: string;

  @Input('placeholder')
  public placeholder: string;

  abstract items: Selects<S>;

  onModelChange(value: string) {
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
