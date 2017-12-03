import {Directive, ElementRef, forwardRef} from '@angular/core';
import {NG_VALUE_ACCESSOR} from "@angular/forms";
import {BinaryFile} from "../../../common/rest/BinaryFile";
import {AbstractFileInput} from "../AbstractFileInput";


export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => InputDirective),
  multi: true
};

@Directive({
  selector: 'meld-file-container input[type=file]',
  exportAs : "InputDirective",
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class InputDirective extends AbstractFileInput {

  private _value: BinaryFile;

  constructor(elementRef: ElementRef) {
    super(elementRef);
  }

  get value(): BinaryFile {
    return this._value;
  }

  set value(value: BinaryFile) {
    this._value = value;
  }

  clear() {
    this.value = null;
    this.onChangeCallback(this.value);
  }

  click() {
    this.elementRef.nativeElement.click();
  }

}
