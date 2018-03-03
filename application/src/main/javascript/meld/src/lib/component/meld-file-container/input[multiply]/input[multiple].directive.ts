import {Directive, ElementRef, forwardRef} from '@angular/core';
import {NG_VALUE_ACCESSOR} from "@angular/forms";
import {AbstractFileInput} from "../AbstractFileInput";
import {BinaryFile} from "../../../common/rest/BinaryFile";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => InputMultiplyDirective),
  multi: true
};

@Directive({
  selector: 'meld-file-container input[type=file] [multiply]',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class InputMultiplyDirective extends AbstractFileInput {

  private _value: BinaryFile[] = [];

  constructor(elementRef: ElementRef<HTMLInputElement>) {
    super(elementRef);
  }

  get value(): BinaryFile[] {
    return this._value;
  }

  set value(value : BinaryFile[]) {
    this._value = value;
  }

}
