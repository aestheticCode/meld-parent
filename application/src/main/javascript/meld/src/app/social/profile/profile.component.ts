import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material";
import {AppService} from '../../app.service';
import {Configuration} from '../../Configuration';
import {ImageDialogComponent} from './image/image-dialog/image-dialog.component';
import {PhotoDialogComponent} from '../../media/photo/grid/photo-dialog/photo-dialog.component';
import {Photo} from '../../media/photo/form/photo.interfaces';
import {HttpClient} from '@angular/common/http';
import {Profile} from './profile.interfaces';
import {BinaryFile} from '../../../lib/common/rest/BinaryFile';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})
export class ProfileComponent implements OnInit {

  user : Configuration.User;

  profile : Profile;

  constructor(private dialog : MatDialog,
              private http : HttpClient,
              service : AppService) {
    this.user = service.configuration.user;
  }

  ngOnInit(): void {
    this.http.get<Profile>('service/social/profile')
      .subscribe((result) => {
        this.profile = result;
      });
  }

  onDialogClick() {
    let matDialogRef = this.dialog.open(PhotoDialogComponent, {width : '400px'});

    matDialogRef.afterClosed().subscribe((result : Photo) => {
      this.http.post('service/social/profile', {photoId : result.id})
        .subscribe((result) => {
          this.ngOnInit();
        });
    });
  }
}
