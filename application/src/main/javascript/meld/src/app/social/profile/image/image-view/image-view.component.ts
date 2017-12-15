import {Component, OnInit} from '@angular/core';
import {AppService} from '../../../../app.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog} from '@angular/material';
import {Photo} from '../../../../media/photo/form/photo.interfaces';
import {PhotoDialogComponent} from '../../../../media/photo/grid/photo-dialog/photo-dialog.component';
import {HttpClient} from '@angular/common/http';
import {UserForm} from '../../../../usercontrol/user/form/user.interfaces';
import {UserFormModel} from '../../../../usercontrol/user/form/user.classes';

@Component({
  selector: 'app-image-view',
  templateUrl: 'image-view.component.html',
  styleUrls: ['image-view.component.css']
})
export class ImageViewComponent implements OnInit {

  image;

  constructor(private http : HttpClient,
              private route: ActivatedRoute,
              private dialog : MatDialog) {}

  ngOnInit(): void {
    this.route.data.forEach((data: { profile: any }) => {
      this.image = data.profile.image;
    });
  }

  onEdit() {
    let matDialogRef = this.dialog.open(PhotoDialogComponent, {width: '400px'});

    matDialogRef.afterClosed().subscribe((result: Photo) => {
      this.http.post<any>('service/social/profile/user', {photoId: result.id})
        .subscribe((result) => {
          this.image = result.image;
        });
    });

  }


}
