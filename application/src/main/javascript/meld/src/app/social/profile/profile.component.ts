import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {AppService} from '../../app.service';
import {Configuration} from '../../Configuration';
import {PhotoDialogComponent} from '../../media/photo/grid/photo-dialog/photo-dialog.component';
import {Photo} from '../../media/photo/form/photo.interfaces';
import {HttpClient} from '@angular/common/http';
import {Profile} from './profile.interfaces';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: Configuration.User;

  profile: Profile;

  constructor(private dialog: MatDialog,
              private http: HttpClient,
              service: AppService) {
    this.user = service.configuration.user;
  }

  ngOnInit(): void {
    this.http.get<Profile>('service/social/profile/background')
      .subscribe((result) => {
        this.profile = result;
      });
  }

  onDialogClick() {
    let matDialogRef = this.dialog.open(PhotoDialogComponent, {width: '400px'});

    matDialogRef.afterClosed().subscribe((result: Photo) => {
      this.http.post('service/social/profile/background', {photoId: result.id})
        .subscribe((result) => {
          this.ngOnInit();
        });
    });
  }
}
