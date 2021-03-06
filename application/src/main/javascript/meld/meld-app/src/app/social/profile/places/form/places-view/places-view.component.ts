import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Places} from '../../places.interfaces';
import {PlacesModel} from '../../places.classes';
import {MeldRouterService} from '@aestheticcode/meld-lib';

@Component({
  selector: 'app-social-places-view',
  templateUrl: 'places-view.component.html',
  styleUrls: ['places-view.component.css'],
  encapsulation : ViewEncapsulation.None
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
