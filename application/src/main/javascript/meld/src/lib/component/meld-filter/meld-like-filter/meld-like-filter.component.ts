import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Like} from '../../../common/search/expression.interfaces';

@Component({
  selector: 'meld-like-filter',
  templateUrl: 'meld-like-filter.component.html',
  styleUrls: ['meld-like-filter.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldLikeFilterComponent {

  @Input("like")
  public like : Like;

  @Output("filterChange")
  private filterChange : EventEmitter<string> = new EventEmitter<string>();

  modelChange(event :string) {
    this.filterChange.emit(event);
  }
}
