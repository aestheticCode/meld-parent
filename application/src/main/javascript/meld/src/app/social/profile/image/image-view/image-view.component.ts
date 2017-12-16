import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MatDialog} from '@angular/material';
import {Photo} from '../../../../media/photo/form/photo.interfaces';
import {PhotoDialogComponent} from '../../../../media/photo/grid/photo-dialog/photo-dialog.component';
import {HttpClient} from '@angular/common/http';
import {Profile} from '../../profile.interfaces';

@Component({
  selector: 'app-image-view',
  templateUrl: 'image-view.component.html',
  styleUrls: ['image-view.component.css']
})
export class ImageViewComponent implements OnInit {

  profile: Profile;

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.route.data.forEach((data: { profile: any }) => {
      this.profile = data.profile;
    });
  }

  onEdit() {
    let matDialogRef = this.dialog.open(PhotoDialogComponent, {width: '400px'});

    matDialogRef.afterClosed().subscribe((result: Photo) => {
      this.http.post<any>('service/social/profile/user', {photoId: result.id})
        .subscribe((result) => {
          this.profile = result;
        });
    });

  }


}
