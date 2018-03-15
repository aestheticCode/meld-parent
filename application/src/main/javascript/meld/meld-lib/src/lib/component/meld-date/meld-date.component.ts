import {
  Component,
  ComponentFactoryResolver,
  ElementRef,
  HostBinding,
  HostListener,
  Injector,
  Input,
  Optional,
  Self,
  ViewChild,
  ViewContainerRef,
  ViewEncapsulation
} from '@angular/core';
import {MatDialog, MatFormFieldControl} from '@angular/material';
import {NgControl} from '@angular/forms';
import {Strings} from '../../common/utils/Strings';
import {MeldDigitComponent} from './meld-digit/meld-digit.component';
import {MeldMonthComponent} from './meld-month/meld-month.component';
import {MeldSeparatorComponent} from './meld-separator/meld-separator.component';
import {MeldDatePickerComponent} from '../meld-datepicker/meld-datepicker.component';
import {AbstractControl} from '../../common/forms/AbstractControl';
import * as moment_ from 'moment';

const moment = moment_;

const noop = () => {
};

@Component({
  selector: 'meld-date',
  templateUrl: 'meld-date.component.html',
  styleUrls: ['meld-date.component.css'],
  providers: [
    {provide: MatFormFieldControl, useExisting: MeldDateComponent}
  ],
  encapsulation: ViewEncapsulation.None
})
export class MeldDateComponent extends AbstractControl<string> {

  static nextId = 0;

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  value: string;

  @HostBinding()
  id = `meld-date-${MeldDateComponent.nextId++}`;

  get focused(): boolean {
    return false;
  }

  get empty(): boolean {
    return Strings.isEmpty(this.value);
  }

  onContainerClick(event: MouseEvent): void {
    this.open();
  }


  @ViewChild('container', {read: ViewContainerRef})
  container: ViewContainerRef;

  @Input('format')
  format: string = 'YYYYMMMdd';


  constructor(private elementRef: ElementRef<HTMLElement>,
              private resolver: ComponentFactoryResolver,
              @Self() @Optional() ngControl: NgControl,
              private injector: Injector,
              public dialog: MatDialog) {
    super(ngControl);
  }


  readFormat(): string[] {
    const result: string[] = [];
    let index = 0;
    let lastChar = this.format.charAt(0);
    for (let i = 0; i < this.format.length; i++) {
      const char = this.format.charAt(i);
      if (lastChar != char) {
        result.push(char);
        index++;
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

  onSpanClick(event: MouseEvent): boolean {
    event.stopPropagation();
    return false;
  }

  @HostListener('window:click')
  blur(): void {
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
            this.value = momentDate.format('YYYY-MM-DD');
            this.onChangeCallback(this.value);
          });
          this.container.insert(componentRef.hostView);
        }
          break;
        case 'MM' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.month() + 1;
          componentRef.instance.limit = 12;
          componentRef.instance.readonly = this.readonly;
          componentRef.instance.valueChange.subscribe((value) => {
            momentDate.month(value);
            this.value = momentDate.format('YYYY-MM-DD');
            this.onChangeCallback(this.value);
          });
          this.container.insert(componentRef.hostView);
        }
          break;
        case 'MMM' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldMonthComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.month();
          componentRef.instance.readonly = this.readonly;
          componentRef.instance.valueChange.subscribe((value) => {
            momentDate.month(value);
            this.value = momentDate.format('YYYY-MM-DD');
            this.onChangeCallback(this.value);
          });
          this.container.insert(componentRef.hostView);
        }
          break;
        case 'YY' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = momentDate.year();
          componentRef.instance.limit = 9999;
          componentRef.instance.readonly = this.readonly;
          componentRef.instance.valueChange.subscribe((value) => {
            momentDate.year(value);
            this.value = momentDate.format('YYYY-MM-DD');
            this.onChangeCallback(this.value);
          });
          this.container.insert(componentRef.hostView);
        }
          break;
        case 'YYYY' : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldDigitComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.digits = 4;
          componentRef.instance.limit = 9999;
          componentRef.instance.value = momentDate.year();
          componentRef.instance.readonly = this.readonly;
          componentRef.instance.valueChange.subscribe((value) => {
            momentDate.year(value);
            this.value = momentDate.format('YYYY-MM-DD');
            this.onChangeCallback(this.value);
          });
          this.container.insert(componentRef.hostView);
        }
          break;
        default : {
          const componentFactory = this.resolver.resolveComponentFactory(MeldSeparatorComponent);
          const componentRef = componentFactory.create(this.injector);
          componentRef.instance.value = segment;
          this.container.insert(componentRef.hostView);
        }
      }
    });
  }

  clear() {
    this.value = undefined;
    this.onChangeCallback(this.value);
    this.container.clear();
  }

  open() {
    if (this.dialog.openDialogs.length === 0) {
      let element: HTMLElement = this.nativeElement;
      let clientRect = element.getBoundingClientRect();
      let config = {
        hasBackdrop: true,
        data: {date: this.value}
      };
      let dialogRef = this.dialog.open(MeldDatePickerComponent, config);

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          const format = moment(result).format('YYYY-MM-DD');
          this.value = format;
          this.onChangeCallback(this.value);
          this.processDate();
        }
      });
    }
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

  ngOnDestroy() {
    this.stateChanges.complete();
  }

}
