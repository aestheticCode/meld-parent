import {Component, EventEmitter, Input, Output, ViewEncapsulation} from '@angular/core';
import {Filter} from '../../find.interfaces';

@Component({
  selector: 'app-social-find-filter-form',
  templateUrl: 'filter-form.component.html',
  styleUrls: ['filter-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class FilterFormComponent {

  @Input("filter")
  public filter: Filter;

  @Output("modelChange")
  private modelChange : EventEmitter<boolean> = new EventEmitter<boolean>();

  onModelChange(value : boolean) {
    this.filter.active = value;
    this.modelChange.emit(value);
  }

}
