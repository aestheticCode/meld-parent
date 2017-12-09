import {Component, ContentChild, forwardRef, Input, TemplateRef} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {Objects} from '../../common/utils/Objects';
import {Items} from '../../common/query/Items';
import {QueryBuilder} from '../../common/query/QueryBuilder';

const noop = () => {
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldSelectViewComponent),
  multi: true
};

@Component({
  selector: 'meld-select-view',
  templateUrl: 'meld-select-view.component.html',
  styleUrls: ['meld-select-view.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class MeldSelectViewComponent implements ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  public value: any;

  @ContentChild(TemplateRef)
  public template: TemplateRef<any>;

  @Input('items')
  public items: Items<any>;

  public item: any;

  writeValue(obj: any): void {
    if (Objects.isNotNull(obj)) {
      this.value = obj;
      let query = QueryBuilder.query();
      query.predicate = this.predicate([this.value]);
      this.items(query, (data, size) => {
        this.item = data[0];
      });
    }
  }

  @Input('predicate')
  public predicate = (values) => {
    if (values == null) {
      return null;
    }
    return QueryBuilder.in(values, 'id');
  };

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }


}
