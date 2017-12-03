import {AfterContentInit, Component, ContentChild, ElementRef, Input} from '@angular/core';
import {InputDirective} from "./input/input.directive";
import {InputMultiplyDirective} from "./input[multiply]/input[multiple].directive";
import {MatFormFieldControl} from "@angular/material";
import {BinaryFile} from "../../common/rest/BinaryFile";
import {Subject} from "rxjs/Subject";
import {NgControl} from "@angular/forms";

@Component({
  selector: 'meld-file-container',
  templateUrl: 'meld-file-container.component.html',
  styleUrls: ['meld-file-container.component.css'],
  providers : [{provide: MatFormFieldControl, useExisting: MeldFileContainerComponent}]
})
export class MeldFileContainerComponent implements MatFormFieldControl<BinaryFile> {

  stateChanges: Subject<void> = new Subject();
  id: string;

  @Input("placeholder")
  placeholder: string;

  ngControl: NgControl | null;
  focused: boolean;
  required: boolean;
  disabled: boolean;
  errorState: boolean;

  setDescribedByIds(ids: string[]): void {
  }

  onContainerClick(event: MouseEvent): void {
  }

  @ContentChild(InputDirective)
  input : InputDirective;

  constructor(private elementRef : ElementRef) { }

  get shouldPlaceholderFloat() {
    return this.focused || ! this.empty;
  }

  get empty() {
    return this.value === null;
  }

  get value() {
    return this.input.value;
  }

  set value(value : BinaryFile) {
    this.input.value = value;
  }

}
