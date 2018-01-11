import {Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {Places} from '../places.interfaces';
import {PlacesModel} from '../places.classes';
import {AddressModel} from '../address.classes';
import {Address} from '../address.interfaces';
import {Strings} from 'lib/common/utils/Strings';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {AbstractForm} from 'lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {AddressFormComponent} from './address-form/address-form.component';
import {Link} from '../../../../../lib/common/rest/Link';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {Place} from '../../../../../lib/component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.interfaces';

@Component({
  selector: 'app-social-places-form',
  templateUrl: 'places-form.component.html',
  styleUrls: ['places-form.component.css']
})
export class PlacesFormComponent extends AbstractForm<Places> implements OnInit {

  public places: FormGroup;

  public links : Link[];

  constructor(private http: HttpClient,
              private builder : FormBuilder,
              private router: MeldRouterService) {
    super();
  }

  ngOnInit() {
    let places: Places = this.router.data.places;

    this.links = places.links;

    this.places = this.builder.group({
      addresses : this.builder.array(places.addresses.map((place) => {return this.builder.group(place)}))
    });
  }

  get addresses() {
    return this.places.get("addresses") as FormArray
  }

  onCreateAddress() {
    this.addresses.push(this.builder.group({
      location : this.builder.control(null),
      start : this.builder.control(null),
      end : this.builder.control(null),
      tillNow : this.builder.control(false)
    }))
  }

  onDeleteAddress(index : number) {
    this.addresses.removeAt(index);
  }

  public preRequest(): boolean {
    return this.validateAllFields(this.places);
  }

  public saveRequest(): Observable<Places> {
    return this.http.post<Places>('service/social/user/current/places', this.places.getRawValue());
  }

  public updateRequest(): Observable<Places> {
    return this.http.put<Places>('service/social/user/current/places', this.places.getRawValue());
  }

  public deleteRequest(): Observable<Places> {
    return this.http.delete<Places>('service/social/user/current/places');
  }

  public postRequest() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['places', 'view']}}]);
  }



}
