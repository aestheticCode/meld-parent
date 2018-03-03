import {Component, forwardRef, Input, OnChanges, SimpleChanges, ViewEncapsulation} from '@angular/core';
import {Http, Response} from "@angular/http";
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {GroupSelect} from "./group-multiselect.interfaces";
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {Selects} from '../../../../../lib/component/meld-combobox/meld-combobox.interfaces';
import {HttpClient} from '@angular/common/http';
import {Container} from '../../../../../lib/common/rest/Container';

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

  constructor(private http: HttpClient) {}

  groups: Selects<GroupSelect> = (query, response) => {

    const params = {
      index : query.index.toString(),
      limit : query.limit.toString(),
      name : query.filter
    };

    this.http.get<Container<GroupSelect>>('service/usercontrol/group/multiselect', {params : params})
      .subscribe((res: Container<GroupSelect>) => {
        response(res.rows, res.size);
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
