import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MatDialog} from '@angular/material';
import {Photo} from '../../../../media/photo/form/photo.interfaces';
import {PhotoDialogComponent} from '../../../../media/photo/grid/photo-dialog/photo-dialog.component';
import {HttpClient} from '@angular/common/http';
import {Profile} from '../../profile.interfaces';
import {MeldRouterService} from '@aestheticcode/meld-lib';

@Component({
  selector: 'app-image-view',
  templateUrl: 'image-view.component.html',
  styleUrls: ['image-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class ImageViewComponent implements OnInit {

  profile: Profile;

  constructor(private http: HttpClient,
              private router: MeldRouterService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.profile = this.router.data.profile;
  }

  onEdit() {
    let matDialogRef = this.dialog.open(PhotoDialogComponent, {width: '400px'});

    matDialogRef.afterClosed().subscribe((result: Photo) => {
      this.http.post<any>('service/social/user/current/profile/user', {photoId: result.id})
        .subscribe((result) => {
          this.profile = result;
        });
    });

  }


}
