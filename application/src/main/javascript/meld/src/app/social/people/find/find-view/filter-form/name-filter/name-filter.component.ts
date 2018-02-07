import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {NameExpression} from '../../../find.interfaces';
import {Strings} from '../../../../../../../lib/common/utils/Strings';

@Component({
  selector: 'app-social-name-filter',
  templateUrl: 'name-filter.component.html',
  styleUrls: ['name-filter.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class NameFilterComponent {

  @Input("expression")
  expression : NameExpression;

  @Output("modelChange")
  modelChange : EventEmitter<boolean> = new EventEmitter<boolean>();

  onModelChange(event : string) {
    this.modelChange.emit(Strings.isNotEmpty(event))
  }


}
