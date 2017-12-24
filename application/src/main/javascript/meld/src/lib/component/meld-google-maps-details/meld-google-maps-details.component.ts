import {Component, ContentChild, Input, OnInit, TemplateRef} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MeldGoogleMapsDetails} from './meld-google-maps-details.intefaces';

@Component({
  selector: 'meld-google-maps-details',
  templateUrl: 'meld-google-maps-details.component.html',
  styleUrls: ['meld-google-maps-details.component.css']
})
export class MeldGoogleMapsDetailsComponent {

  private _id : string;

  public details : MeldGoogleMapsDetails;

  @ContentChild(TemplateRef)
  public content : TemplateRef<any>;

  constructor(private http : HttpClient) { }

  get id(): string {
    return this._id;
  }

  @Input('id')
  set id(value: string) {
    if (value !== this._id) {
      this._id = value;

      this.http.get<MeldGoogleMapsDetails>(`service/generic/google/place/${value}/details`)
        .subscribe((res) => {
          this.details = res;
        })
    }
  }
}
