import {Component, EventEmitter, forwardRef, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {Category} from '../categories.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {Items} from '../../../../../lib/common/search/search.interfaces';

const noop = () => {
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => CategoriesSelectComponent),
  multi: true
};


@Component({
  selector: 'app-categories-select',
  templateUrl: 'categories-select.component.html',
  styleUrls: ['categories-select.component.css'],
  providers : [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
  encapsulation : ViewEncapsulation.None
})
export class CategoriesSelectComponent implements ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  public value : any;

  @Output('onClickChange')
  private onClickChange : EventEmitter<void> = new EventEmitter();

  constructor(private http : HttpClient) { }

  onClick() {
    this.onClickChange.emit();
  }

  categories: Items<Category> = (query, response) => {
    this.http.post<Container<Category>>('service/social/people/categories', query)
      .subscribe((res: Container<Category>) => {
        response(res.rows, res.size);
      });
  };

  writeValue(obj: any): void {
    this.value = obj;
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

}
