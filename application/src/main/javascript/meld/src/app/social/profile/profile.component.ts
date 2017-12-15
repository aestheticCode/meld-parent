import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {AppService} from '../../app.service';
import {Configuration} from '../../Configuration';
import {PhotoDialogComponent} from '../../media/photo/grid/photo-dialog/photo-dialog.component';
import {Photo} from '../../media/photo/form/photo.interfaces';
import {HttpClient} from '@angular/common/http';
import {Profile} from './profile.interfaces';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})
export class ProfileComponent implements OnInit {

  id : string;

  profile: Profile;

  constructor(private dialog: MatDialog,
              private http: HttpClient,
              private route: ActivatedRoute) {
  }


  ngOnInit(): void {
    this.route.params.subscribe( params => {
      this.id = params.id;
    });

    this.route.data.forEach((data: { profile: any }) => {
      this.profile = data.profile;
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
