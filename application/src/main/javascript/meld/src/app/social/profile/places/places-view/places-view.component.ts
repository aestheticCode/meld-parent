import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Places} from '../places.interfaces';
import {PlacesModel} from '../places.classes';
import {MeldRouterService} from '../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-social-places-view',
  templateUrl: 'places-view.component.html',
  styleUrls: ['places-view.component.css']
})
export class PlacesViewComponent implements OnInit {

  public places: Places;

  constructor(private router: MeldRouterService) {
  }

  ngOnInit() {
    this.places = this.router.data.places || new PlacesModel();
  }

  onEdit() {
    this.router.navigate(['social', 'profile', this.router.param.id, {outlets: {profile: ['places', 'edit']}}]);
  }

  onCancel() {
    this.router.navigate(['social', 'profile', this.router.param.id]);
    }


}
