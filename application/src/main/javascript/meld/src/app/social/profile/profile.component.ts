import {Component} from '@angular/core';
import {MatDialog} from "@angular/material";
import {AppService} from '../../app.service';
import {Configuration} from '../../Configuration';
import {ImageDialogComponent} from './image/image-dialog/image-dialog.component';
import {PhotoDialogComponent} from '../../media/photo/grid/photo-dialog/photo-dialog.component';
import {Photo} from '../../media/photo/form/photo.interfaces';

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
    let matDialogRef = this.dialog.open(PhotoDialogComponent, {width : '400px'});

    matDialogRef.afterClosed().subscribe((result : Photo) => {

    });
  }
}
