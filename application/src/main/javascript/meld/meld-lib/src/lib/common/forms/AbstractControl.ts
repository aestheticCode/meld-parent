import {ControlValueAccessor, NgControl} from '@angular/forms';
import {Subject} from 'rxjs/Subject';
import {HostBinding, Input} from '@angular/core';
import {MatFormFieldControl} from '@angular/material';

export abstract class AbstractControl<V> implements ControlValueAccessor, MatFormFieldControl<V> {

  abstract id: string;
  abstract value: V;

  onContainerClick(event: MouseEvent): void {
    throw new Error('Method not implemented.');
  }

  stateChanges: Subject<void> = new Subject<void>();

  @HostBinding('attr.aria-describedby')
  describedBy = '';

  @Input('placeholder')
  placeholder: string;

  @Input('required')
  _required: any = false;

  @Input('disabled')
  disabled: boolean = false;

  @Input('readonly')
  readonly: boolean = false;

  abstract get empty();

  abstract get focused();

  get errorState(): boolean {
    return this.ngControl.touched && !this.ngControl.valid;
  }

  get shouldPlaceholderFloat() {
    return this.focused || !this.empty;
  }

  get required() {
    return this._required === true || this._required === '';
    //return false;
  }

  setDescribedByIds(ids: string[]) {
    this.describedBy = ids.join(' ');
  }

  constructor(public ngControl: NgControl) {
    if (this.ngControl) {
      this.ngControl.valueAccessor = this;
    }
  }

  abstract writeValue(obj: any);

  abstract registerOnChange(fn: any);

  abstract registerOnTouched(fn: any);


}
