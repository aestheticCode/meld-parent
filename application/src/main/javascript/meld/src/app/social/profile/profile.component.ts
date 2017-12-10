import {Component} from '@angular/core';
import {MatDialog} from "@angular/material";
import {ImageDialogComponent} from "./image-dialog/image-dialog.component";
import {AppService} from '../../app.service';
import {Configuration} from '../../Configuration';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})
export class ProfileComponent {

  user : Configuration.User;

  constructor(private dialog : MatDialog,
              service : AppService) {
    this.user = service.configuration.user;
  }

  onDialogClick() {
    this.dialog.open(ImageDialogComponent)
  }
}
