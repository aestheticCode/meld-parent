import {Component, EventEmitter, Input, Output, ViewEncapsulation} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {Observable} from 'rxjs/Observable';
import {Strings} from '../../../../../../../lib/common/utils/Strings';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-social-company-form',
  templateUrl: 'company-form.component.html',
  styleUrls: ['company-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CompanyFormComponent {

  @Input('company')
  public company: FormGroup;

  @Output('delete')
  private delete: EventEmitter<any> = new EventEmitter<any>();

  public names: Observable<string>;


  constructor(private http : HttpClient) {}

  onDelete() {
    this.delete.emit();
  }

  findNames(value : string) : Observable<any> {
    if (Strings.isEmpty(value)) {
      return Observable.of(null);
    }
    this.names = this.http.get<any>("service/social/work/history/name", {params : {name : value}})
  }

}
