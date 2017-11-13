import {
  Component, ComponentFactoryResolver, forwardRef, HostListener, Injector, Input, OnInit, Optional,
  Self, ViewChild, ViewContainerRef
} from '@angular/core';
import {MdFormFieldControl} from "@angular/material";
import {ControlValueAccessor, NG_VALUE_ACCESSOR, NgControl} from "@angular/forms";
import {Strings} from "../../common/utils/Strings";
import {Subject} from "rxjs/Subject";
import {MeldDigitComponent} from "./meld-digit/meld-digit.component";
import {MeldMonthComponent} from "./meld-month/meld-month.component";
import {MeldSeparatorComponent} from "./meld-separator/meld-separator.component";
import * as moment from 'moment';

const noop = () => {};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldDateComponent),
  multi: true
};

@Component({
  selector: 'meld-date',
  templateUrl: 'meld-date.component.html',
  styleUrls: ['meld-date.component.css'],
  providers: [
    CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR,
    {provide: MdFormFieldControl, useExisting: MeldDateComponent}
  ]
})
export class MeldDateComponent implements MdFormFieldControl<string>, ControlValueAccessor, OnInit {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  value: string = moment(new Date()).format("YYYY-MM-DD");
  stateChanges: Subject<void> = new Subject<void>();
  id: string;

  @Input("placeholder")
  placeholder: string;
  @Input("format")
  format : string = "YYYY/MM/dd";
  focused: boolean;
  empty: boolean = false;
  required: boolean;
  disabled: boolean;
  errorState: boolean;

  @ViewChild('container', { read: ViewContainerRef })
  container : ViewContainerRef;

  setDescribedByIds(ids: string[]): void {
  }

  readFormat() : string[] {
    const result : string[] = [];
    let index = 0;
    let lastChar = this.format.charAt(0);
    for (let i = 0; i < this.format.length; i++) {
      const char = this.format.charAt(i);
      if (lastChar != char) {
        result.push(char);
        index ++;
      } else {
        if (result.length < index + 1) {
          result.push(char);
        } else {
          result[index] += char;
        }
      }
      lastChar = char;
    }
    return result;
  }

  focus(): void {
    this.focused = true;
    this.stateChanges.next();
  }

  onSpanClick(event: MouseEvent): boolean {
    event.stopPropagation();
    this.focus();
    return false;
  }

  @HostListener("window:click")
  blur(): void {
    this.focused = false;
    this.stateChanges.next();
  }

  constructor(@Optional() @Self() public ngControl: NgControl,
              private resolver : ComponentFactoryResolver,
              private injector : Injector) {}

  ngOnInit() {
    let format = this.readFormat();
    let momentDate = moment(this.value);

    format.forEach((segment) => {
      switch (segment) {
        case 'dd' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.date();
          componentRef.instance.limit = momentDate.daysInMonth();
          this.container.insert(componentRef.hostView);
        } break;
        case 'MM' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.month() + 1;
          componentRef.instance.limit = 12;
          this.container.insert(componentRef.hostView);
        } break;
        case 'MMM' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldMonthComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.month();
          this.container.insert(componentRef.hostView);
        } break;
        case 'YY' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.year();
          componentRef.instance.limit = 9999;
          this.container.insert(componentRef.hostView);
        } break;
        case 'YYYY' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.digits = 4;
          componentRef.instance.limit = 9999;
          componentRef.instance.value = momentDate.year();
          this.container.insert(componentRef.hostView);
        } break;
        default : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldSeparatorComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = segment;
          this.container.insert(componentRef.hostView);
        }
      }
    })
  }

  writeValue(obj: any): void {
    if (Strings.isString(obj)) {
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
