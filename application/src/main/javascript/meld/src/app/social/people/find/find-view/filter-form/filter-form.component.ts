import {Component, EventEmitter, Output, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-social-find-filter-form',
  templateUrl: 'filter-form.component.html',
  styleUrls: ['filter-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class FilterFormComponent {

  public name: string;

  public school: string;

  @Output("search")
  private search : EventEmitter<void> = new EventEmitter<void>();

  constructor(protected http: HttpClient) {}

  onSearchClick() {
    this.search.emit();
  }

}
