import {Component, forwardRef, Input, OnChanges, SimpleChanges, ViewEncapsulation} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {GroupSelect} from "./group-multiselect.interfaces";
import {Items} from '../../../../../lib/common/search/search.interfaces';

const noop = () => {
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => GroupMultiselectComponent),
  multi: true
};


@Component({
  selector: 'app-group-multiselect',
  templateUrl: 'group-multiselect.component.html',
  styleUrls: ['group-multiselect.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
  encapsulation : ViewEncapsulation.None
})
export class GroupMultiselectComponent implements ControlValueAccessor, OnChanges {

  public value: any[];

  @Input("placeholder")
  public placeholder: string;

  @Input("readonly")
  public readonly : boolean = false;

  @Input("disabled")
  public disabled : boolean = false;

  @Input("hideComboBox")
  public hideComboBox : boolean = false;

  private onTouchedCallback: () => void = noop;

  private onChangeCallback: (value: any) => void = noop;

  constructor(private http: Http) {
  }

  groups: Items<GroupSelect> = (query, response) => {
    this.http.post('service/usercontrol/group/multiselect', query)
      .subscribe((res: Response) => {
        const json = res.json();
        response(json.rows, json.size);
      });
  };

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['value']) {
      this.onChangeCallback(this.value);
    }
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
