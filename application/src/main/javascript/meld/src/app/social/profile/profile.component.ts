import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MatDialog} from '@angular/material';
import {PhotoDialogComponent} from '../../media/photo/grid/photo-dialog/photo-dialog.component';
import {Photo} from '../../media/photo/form/photo.interfaces';
import {HttpClient} from '@angular/common/http';
import {Profile} from './profile.interfaces';
import {ActivatedRoute} from '@angular/router';
import {MeldRouterService} from '../../../lib/service/meld-router/meld-router.service';
import {AppService} from '../../app.service';

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {

  id: string;

  profile: Profile;

  constructor(private dialog: MatDialog,
              private http: HttpClient,
              private service : AppService,
              private route: MeldRouterService) {
  }


  ngOnInit(): void {
    this.id = this.route.param.id;
    this.profile = this.route.data.profile;
  }

  onDialogClick() {
    let matDialogRef = this.dialog.open(PhotoDialogComponent, {width: '400px'});

    matDialogRef.afterClosed().subscribe((result: Photo) => {
      this.http.post('service/social/user/current/profile/background', {photoId: result.id})
        .subscribe((result) => {
          this.ngOnInit();
        });
    });
  }

  onPostsClick() {
    let id;
    if (this.id === "current") {
      id = this.service.configuration.user.id;
    } else {
      id = this.id;
    }
    this.route.navigate(["/channel", "meld", "posts"], { queryParams : {profile : true, id : id} })
  }
}
