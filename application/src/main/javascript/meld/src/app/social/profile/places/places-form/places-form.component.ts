import {Component, OnInit} from '@angular/core';
import {Places} from '../places.interfaces';
import {PlacesModel} from '../places.classes';
import {AddressModel} from '../address.classes';
import {Address} from '../address.interfaces';
import {Strings} from 'lib/common/utils/Strings';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {AbstractForm} from 'lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {GroupForm} from '../../../../usercontrol/group/form/group-form/group-form.interfaces';

@Component({
  selector: 'app-social-places-form',
  templateUrl: 'places-form.component.html',
  styleUrls: ['places-form.component.css']
})
export class PlacesFormComponent extends AbstractForm<Places> implements OnInit {

  public places: Places;
  private router: MeldRouterService;

  constructor(http: HttpClient,
              router: MeldRouterService) {
    super(http);
    this.router = router;
  }

  ngOnInit() {
    this.places = this.router.data.places || new PlacesModel();
  }

  onCreateAddress() {
    this.places.addresses.push(new AddressModel());
  }

  onDeleteAddress(address: Address) {
    if (this.places.addresses.length > 0) {
      let indexOf = this.places.addresses.indexOf(address);
      this.places.addresses.splice(indexOf, 1);
    }
  }

  onEdit() {
    if (this.places.addresses.length === 0) {
      this.places.addresses.push(new AddressModel());
    }
  }

  public saveRequest(): Observable<Places> {
    return this.http.post<Places>( 'service/social/user/current/places', this.places)
  }

  public updateRequest(): Observable<Places> {
    return this.http.put<Places>('service/social/user/current/places', this.places)
  }

  public deleteRequest(): Observable<Places> {
    return this.http.delete<Places>('service/social/user/current/places')
  }

  public preRequest() {
    this.filterEmptyAddress();
  }

  public postRequest() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['places', 'view']}}]);
  }

  private filterEmptyAddress() {
    this.places.addresses
      = this.places.addresses.filter((address) => {
      return Strings.isNotEmpty(address.location.street)
        || Strings.isNotEmpty(address.location.zipCode)
        || Strings.isNotEmpty(address.location.state)
        || Strings.isNotEmpty(address.location.streetNumber)
        || Strings.isNotEmpty(address.location.country);
    });
  }

}
