import {
  Component,
  ContentChild,
  DoCheck,
  ElementRef,
  forwardRef,
  HostListener,
  Inject,
  Input,
  OnInit,
  TemplateRef,
  ViewChild
} from "@angular/core";
import {ControlValueAccessor, FormControl, NG_VALIDATORS, NG_VALUE_ACCESSOR, NgModel} from "@angular/forms";
import {GpmComboboxParams} from "component/gpm-combobox/gpm-combobox.params";
import {GpmComboboxItemsFunction} from "component/gpm-combobox/gpm-combobox.items.function";
import {GpmWindowRowIndex} from '../gpm-window/gpm-window.row.index';
import {GpmWindowViewPort} from "component/gpm-window/gpm-window.viewport";
import {GpmShortcutHandler} from "../../app/rrc/gpm-shortcuts/gpm-shortcut-handler.component";
import {DOCUMENT} from "@angular/platform-browser";
import {BookingService} from '../../app/booking/sharedBooking/booking.service';


export const CUSTOM_INPUT_CONTROLVALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => GpmComboboxComponent),
  multi: true
};

export const CUSTOM_INPUT_NG_VALIDATORS: any = {
  provide: NG_VALIDATORS,
  useExisting: forwardRef(() => GpmComboboxComponent),
  multi: true
};

@Component({
  selector: 'gpm-combobox',
  templateUrl: 'gpm-combobox.component.html',
  styleUrls: ['gpm-combobox.component.css'],
  providers: [CUSTOM_INPUT_CONTROLVALUE_ACCESSOR, CUSTOM_INPUT_NG_VALIDATORS],
  host: {
    'style': 'display: flex;'
  }
})
export class GpmComboboxComponent implements OnInit, DoCheck, ControlValueAccessor {

  private onTouchedCallback: () => {};
  private onChangeCallback: (value: any) => {};

  public inputValue: string = "";

  public position: number = 0;

  public showSelection: boolean;

  public showItems: boolean;

  public itemsWindow: any[] = [];

  public itemsSize: number;

  public itemsToLoad: number = 0;

  public itemIndex: number = 0;

  /**
   * last index is used to determine if any progress has been made when contacting the db.
   * if not (lastStartIndex == event.index) then there will be no request.
   * this mainly fixes a bug when u scroll to the end of the list and endless request are beeing fired for no reason
   * @type {number}
   */
  private lastStartIndex: number = 0;

  public value: any[] = [];

  public selectedItems: any[] = [];

  @Input()
  private isValid: boolean = true;

  public valid() {
    return this.isValid;
  };

  public setValid(value: boolean) {
    this.isValid = value;
  };

  private itemHoverIndex: number = 0;

  private windowViewPort: GpmWindowViewPort = new GpmWindowViewPort(0, 0, false);

  public inputHasFocus: boolean = false;

  @Input()
  public required: any = false;

  @Input()
  public useCheckBoxes: boolean = false;

  @Input()
  public disabled: boolean = false;

  @Input()
  public items: GpmComboboxItemsFunction;

  @Input()
  public multiSelect: boolean = true;

  @Input()
  public max_height: string = "210";

  @Input()
  public all: boolean = false;

  @Input()
  public itemValuePath: string;

  @Input()
  public itemLabelPath: string;

  @ContentChild(TemplateRef)
  private templateRef: TemplateRef<any>;

  @ViewChild('dropDown')
  public dropDown: ElementRef;

  @ViewChild('input')
  public input: ElementRef;

  @ViewChild('inputModel')
  public inputModel: NgModel;

  public showWaitSpinner = true;

  @ViewChild('waitSpinner')
  public waitSpinner: ElementRef;

  ngOnInit(): void {
    this.inputModel
      .valueChanges
      .debounceTime(300)
      .subscribe((value: string) => {
        this.showWaitSpinner = true;

        if (this.items == null) {
          throw new Error("No items set");
        }
        this.items(new GpmComboboxParams(0, this.itemsToLoad, value), (data: any[], size: number) => {
          this.itemsWindow = data;
          this.itemsSize = size;
          this.showWaitSpinner = false;
        });
      });

    let inputElement: HTMLInputElement = this.input.nativeElement;
    inputElement.disabled = this.disabled;
  }

  constructor(private shortCutHandler: GpmShortcutHandler,
              @Inject(DOCUMENT) private document: any,
              private sharedBookingService: BookingService) {
  }

  validate(control: FormControl) {
    if (this.required || this.required === "") {
      if (control.pristine) {
        this.isValid = true;
        return null;
      }

      if (control.value && control.value.length > 0) {
        this.isValid = true;
        return null;
      } else {
        this.isValid = false;
        return {required: true}
      }
    }
  }

  public onInputClick(event: MouseEvent) {
    return false;
  }

  public onInputKeyUp(event: KeyboardEvent) {
    if(! event.ctrlKey && ! event.altKey){
      this.showItemsDropDown();
    }

    switch (event.keyCode) {
      case 13 : {
        if (this.itemsWindow.length > 0) {
          const itemValue = this.parse(this.itemValuePath, this.itemsWindow[this.windowViewPort.start + this.itemHoverIndex]);
          if (this.value.indexOf(itemValue) == -1) {
            if(this.multiSelect){
              this.value.push(itemValue);
            }
            else {
              this.value = [itemValue];
            }
            this.onChangeCallback(this.value);
            this.inputValue = "";
            this.hideDropDown();
            event.stopPropagation();
            return false;
          }
        }
      }
        break;
      case 38 : {
        if (this.itemHoverIndex > 0) {
          this.itemHoverIndex--;
        } else {
          if (this.position > 0) {
            this.position -= 1 / this.itemsSize;
          }
        }
      }
        break;
      case 40 : {
        if (this.itemHoverIndex < this.windowViewPort.count - 1) {
          this.itemHoverIndex++;
        } else {
          if (this.position < 1 && this.windowViewPort.scrollBar) {
            this.position += 1 / this.itemsSize;
          }
        }
      }
        break;
    }
  }

  private itemHover(index: number): boolean {
    return this.windowViewPort.start + this.itemHoverIndex == index;
  }

  private parse(path: string, obj: any): any {
    if (!obj) {
      return "";
    }
    const paths = path.split('.');
    let current = obj, i;
    for (i = 0; i < paths.length; ++i) {
      if (current[paths[i]] == null) {
        return undefined;
      } else {
        current = current[paths[i]];
      }
    }
    return current;
  }

  private select(item, event): void {
    const selected = event.srcElement.checked;
    const itemValue = this.parse(this.itemValuePath, item);
    if (selected) {
      if (!this.multiSelect) {
        this.value = [];
      }
      this.value.push(itemValue);
    } else {
      const indexOf = this.value.indexOf(itemValue);
      this.value.splice(indexOf, 1)
    }
    this.onChangeCallback(this.value);
  }

  private selectClick(item): void {
    const itemValue = this.parse(this.itemValuePath, item);
    if (this.multiSelect) {
      if (this.value.indexOf(itemValue) == -1) {
        this.value.push(itemValue);
      }
    } else {
      this.value = [itemValue];
    }
    this.onChangeCallback(this.value);
    this.input.nativeElement.value = "";
    this.hideDropDown();
  }

  public clearSelection() {
    this.value = [];
    this.onChangeCallback(this.value);
    this.sharedBookingService.popOverMessage = "";
    this.selectedItems = [];
    this.focus();
  }

  private selectionSize(): string {
    if (this.all && !this.disabled) {
      if (this.value.length == 0) {
        return "All";
      } else {
        return this.value.length.toString();
      }
    } else {
      return this.value.length.toString();
    }
  }

  private isSelected(item): boolean {
    const itemValue = this.parse(this.itemValuePath, item);
    return this.value.indexOf(itemValue) != -1;
  }

  private deselectItem(value): void {
    this.selectedItems.forEach((item) => {
      if (value == item) {
        const indexOf = this.selectedItems.indexOf(item);
        this.selectedItems.splice(indexOf, 1);
        this.value.splice(indexOf, 1);
      }
    });
    if (this.selectedItems.length == 0) {
      this.hideDropDown();
      this.input.nativeElement.value = "";
      this.input.nativeElement.placeholder = "";
    }
    if (this.onChangeCallback) {
      this.onChangeCallback(this.value);
    }
  }

  public showItemsOnClick(): void {
    //console.log("showItemsOnClick");
    this.showItemsDropDown();
    this.showWaitSpinner = true;

    this.items(new GpmComboboxParams(this.itemIndex, this.itemsToLoad, this.inputValue), (data: any[], size: number) => {
      this.itemsWindow = data;
      this.itemsSize = size;
      this.itemHoverIndex = 0;
      this.showWaitSpinner = false;
    });
  }

  private showSelectionOnClick(): void {
    this.showSelectionDropDown();
    this.showWaitSpinner = true;

    if (this.value.length > 0) {
      this.items(new GpmComboboxParams(0, this.value.length, this.inputValue, this.value), (data: any[], size: number) => {
        this.selectedItems = this.sortSelectedItems(data);
        this.showWaitSpinner = false;
        //console.log(JSON.stringify(data));
      });
    }
  }

  private showItemsDropDown() {
    //console.log("showItemsDropDown");
    if (!this.disabled) {
      //console.log("showItemsDropDown enabled");
      this.showDropDown();
      this.showItems = true;
      this.showSelection = false;
    }
  }

  private showSelectionDropDown() {
    if (!this.disabled) {
      this.showDropDown();
      this.showItems = false;
      this.showSelection = true;
    }
  }

  private hideDropDown(): void {
    this.dropDown.nativeElement.style.display = 'none';
  }

  public showDropDown(): void {
    //console.log("showDropDown ");
    this.dropDown.nativeElement.style.display = 'flex';

    if (Number(this.max_height) > 0) {
      this.dropDown.nativeElement.style.maxHeight = this.max_height + "px";
    }
  }

  public clickDropDown(event: MouseEvent): void {
    event.preventDefault();
  }

  @HostListener('document:mousedown', ['$event'])
  public closeDropDownOnDocumentMousedown(event: MouseEvent): void {
    if (!event.defaultPrevented) {
      this.hideDropDown();
    }
  }

  @HostListener('window:resize')
  public resizeDropDown(): void {
    const input: HTMLElement = this.input.nativeElement;
    const dropDown: HTMLElement = this.dropDown.nativeElement;
    const width = input.offsetWidth - 1;
    dropDown.style.width = width + 'px';
  }

  ngDoCheck(): void {
    this.resizeDropDown();
    if (this.inputHasFocus) {
      this.itemsToLoad = 75
    }


    if (this.value.length == 0) {
      this.input.nativeElement.placeholder = "";
    }
  }


  public hasFocus(): boolean {
    return this.inputHasFocus || this.input.nativeElement == this.document.activeElement;
  }

  public focus() {
    this.storePlaceholderValues();
    let nativeElement: HTMLInputElement = this.input.nativeElement;
    nativeElement.disabled = false;
    this.inputHasFocus = true;
    nativeElement.focus();
    this.inputValue = "";
    this.position = 0;
  }

  /**
   * to set focus on combobox by initial start
   */
  public focusOnCombobox() {
    setTimeout(() => {
      this.input.nativeElement.focus();
      this.inputHasFocus = true;
    })
  }

  public blur(): void {
    this.inputHasFocus = false;
    if (this.onTouchedCallback) {
      this.onTouchedCallback();
    }
    this.hideDropDown();
    this.input.nativeElement.value = "";
  }

  /**
   * shows the selected elements for this component.<br>
   *     name if only one is selected, names or codes concatinated if several are slected.<br>
   *
   */
  private storePlaceholderValues() {
    if (this.value.length > 0) {
      this.items(new GpmComboboxParams(0, this.value.length, this.inputValue, this.value), (data: any[], size: number) => {
        this.selectedItems = this.sortSelectedItems(data);

        let placeHolder = "";
        for (let item of this.selectedItems) {
          placeHolder += this.parse(this.itemLabelPath, item) + ";";
        }
        placeHolder = placeHolder.substring(0, placeHolder.lastIndexOf(";"));
        this.input.nativeElement.placeholder = placeHolder;
      });
    } else {
      this.input.nativeElement.placeholder = "";
    }
  }

  private sortSelectedItems(items: any[]): any[] {
    let result: any[] = [];

    this.value.forEach((id) => {
      result.push(items.find((item) => {
        return this.parse(this.itemValuePath, item) == id
      }));
    });

    return result;
  }

  private itemIndexChange(event: GpmWindowRowIndex): void {
    this.items(new GpmComboboxParams(event.index, event.itemsToLoad, this.inputValue), (data: any[], size: number) => {

      this.itemsWindow = data;
      this.itemsSize = size;
      this.itemIndex = event.index;
      if (event.index != this.lastStartIndex) {
        console.log("1   event.index: " + event.index + " > this.itemIndex " + this.itemIndex);
        event.callback();
        this.lastStartIndex = event.index;
      }
    });
  }

  public showPlaceholder(){
    return this.value.length > 0;
  }

  writeValue(obj: any): void {
    if (obj == null) {
      this.value = [];
    } else {
      this.value = obj;
      this.storePlaceholderValues();
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = (value: any) => {
      fn(value);
      this.storePlaceholderValues();
      return "";
    };
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

}
