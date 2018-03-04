import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Places} from '../places.interfaces';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {AbstractForm} from 'lib/common/forms/AbstractForm';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Link} from '../../../../../lib/common/rest/Link';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {EducationDialogComponent} from '../../education/education-form/education-dialog/education-dialog.component';
import {PlacesDialogComponent} from './places-dialog/places-dialog.component';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-social-places-form',
  templateUrl: 'places-form.component.html',
  styleUrls: ['places-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PlacesFormComponent extends AbstractForm<Places> implements OnInit {

  public places: FormGroup;

  public links: Link[];

  constructor(private http: HttpClient,
              private dialog : MatDialog,
              private builder: FormBuilder,
              private router: MeldRouterService) {
    super();
  }

  ngOnInit() {
    let places: Places = this.router.data.places;

    this.links = places.links;

    this.places = this.builder.group({
      id : this.builder.control(places.id),
      categories : this.builder.control(places.categories),
      addresses: this.builder.array(places.addresses.map((place) => {
        return this.builder.group(place);
      }))
    });
  }

  get addresses() {
    return this.places.get('addresses') as FormArray;
  }

  onCreateAddress() {
    this.addresses.push(this.builder.group({
      location: this.builder.control(null),
      start: this.builder.control(null),
      end: this.builder.control(null),
      tillNow: this.builder.control(false)
    }));
  }

  onDeleteAddress(index: number) {
    this.addresses.removeAt(index);
  }

  onVisibility() {
    this.dialog.open(PlacesDialogComponent, {data : this.places, width : 400 + 'px'});
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
