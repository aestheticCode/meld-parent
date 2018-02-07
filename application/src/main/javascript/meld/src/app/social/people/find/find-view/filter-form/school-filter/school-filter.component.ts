import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {SchoolExpression} from '../../../find.interfaces';
import {Strings} from '../../../../../../../lib/common/utils/Strings';

@Component({
  selector: 'app-social-school-filter',
  templateUrl: 'school-filter.component.html',
  styleUrls: ['school-filter.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class SchoolFilterComponent  {

  @Input("expression")
  expression : SchoolExpression;

  @Output("modelChange")
  modelChange : EventEmitter<boolean> = new EventEmitter<boolean>();

  onModelChange(event : string) {
    this.modelChange.emit(Strings.isNotEmpty(event))
  }

}
