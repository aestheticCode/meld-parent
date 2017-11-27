import {
  Component, ComponentFactoryResolver, ElementRef, forwardRef, HostListener, Injector, Input, OnInit, Optional,
  Self, ViewChild, ViewContainerRef
} from '@angular/core';
import {MatFormFieldControl, MatDialog} from "@angular/material";
import {ControlValueAccessor, NG_VALUE_ACCESSOR, NgControl} from "@angular/forms";
import {Strings} from "../../common/utils/Strings";
import {Subject} from "rxjs/Subject";
import {MeldDigitComponent} from "./meld-digit/meld-digit.component";
import {MeldMonthComponent} from "./meld-month/meld-month.component";
import {MeldSeparatorComponent} from "./meld-separator/meld-separator.component";
import * as moment from 'moment';
import {MeldDatePickerComponent} from "../meld-datepicker/meld-datepicker.component";

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
    {provide: MatFormFieldControl, useExisting: MeldDateComponent}
  ]
})
export class MeldDateComponent implements MatFormFieldControl<string>, ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  private _value: string;

  stateChanges: Subject<void> = new Subject<void>();

  id: string;

  @Input("placeholder")
  placeholder: string;

  @Input("format")
  format : string = "YYYYMMMdd";

  focused: boolean = false;

  empty: boolean = false;

  @Input("required")
  required: boolean = false;

  @Input("disabled")
  disabled: boolean = false;

  @Input("readonly")
  readonly : boolean = false;

  errorState: boolean = false;

  controlType: string = 'meld-date';

  @ViewChild('container', { read: ViewContainerRef })
  container : ViewContainerRef;

  @Optional() @Self() public ngControl: NgControl;

  constructor(private elementRef : ElementRef,
              private resolver : ComponentFactoryResolver,
              private injector : Injector,
              public dialog: MatDialog) {
    this.empty = Strings.isEmpty(this.value);
  }



  get shouldPlaceholderFloat() {
    return this.focused || !this.empty;
  }


  get value(): string {
    return this._value;
  }

  set value(value: string) {
    this._value = value;
    this.empty = false;
    this.onChangeCallback(this.value);
  }

  setDescribedByIds(ids: string[]): void {
  }

  onContainerClick(event: MouseEvent): void {
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

  get nativeElement() {
    return this.elementRef.nativeElement;
  }

  processDate() {
    let format = this.readFormat();
    let momentDate = moment(this.value);
    this.container.clear();

    format.forEach((segment) => {
      switch (segment) {
        case 'dd' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.date();
          componentRef.instance.limit = momentDate.daysInMonth();
          componentRef.instance.readonly = this.readonly;
          componentRef.instance.valueChange.subscribe((value) => {
            momentDate.date(value);
            this.value = momentDate.format("YYYY-MM-DD");
            this.onChangeCallback(this.value);
          });
          this.container.insert(componentRef.hostView);
        } break;
        case 'MM' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.month() + 1;
          componentRef.instance.limit = 12;
          componentRef.instance.readonly = this.readonly;
          componentRef.instance.valueChange.subscribe((value) => {
            momentDate.month(value);
            this.value = momentDate.format("YYYY-MM-DD");
            this.onChangeCallback(this.value);
          });
          this.container.insert(componentRef.hostView);
        } break;
        case 'MMM' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldMonthComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.month();
          componentRef.instance.readonly = this.readonly;
          componentRef.instance.valueChange.subscribe((value) => {
            momentDate.month(value);
            this.value = momentDate.format("YYYY-MM-DD");
            this.onChangeCallback(this.value);
          });
          this.container.insert(componentRef.hostView);
        } break;
        case 'YY' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.year();
          componentRef.instance.limit = 9999;
          componentRef.instance.readonly = this.readonly;
          componentRef.instance.valueChange.subscribe((value) => {
            momentDate.year(value);
            this.value = momentDate.format("YYYY-MM-DD");
            this.onChangeCallback(this.value);
          });
          this.container.insert(componentRef.hostView);
        } break;
        case 'YYYY' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.digits = 4;
          componentRef.instance.limit = 9999;
          componentRef.instance.value = momentDate.year();
          componentRef.instance.readonly = this.readonly;
          componentRef.instance.valueChange.subscribe((value) => {
            momentDate.year(value);
            this.value = momentDate.format("YYYY-MM-DD");
            this.onChangeCallback(this.value);
          });
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

  clear() {
    this.value = undefined;
    this.onChangeCallback(this.value);
    this.container.clear();
    this.empty = true;
  }

  open() {
    let element : HTMLElement = this.nativeElement;
    let clientRect = element.getBoundingClientRect();
    let config = {
      hasBackdrop : false,
      position : {
        top: clientRect.top - 400 + 'px',
        left : clientRect.left + "px"
      },
      data : {date : this.value}
    };
    let dialogRef = this.dialog.open(MeldDatePickerComponent, config);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const format = moment(result).format("YYYY-MM-DD");
        this.value = format;
        this.processDate();
      }
    });
  }


  writeValue(obj: any): void {
    if (Strings.isString(obj)) {
      this.value = obj;
      this.processDate();
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

}
