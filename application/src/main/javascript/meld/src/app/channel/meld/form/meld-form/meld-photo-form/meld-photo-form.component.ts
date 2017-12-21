import {Component, OnInit} from '@angular/core';
import {MeldPhotoPost} from './meld-photo-form.interfaces';
import {MatDialog} from '@angular/material';
import {Objects} from '../../../../../../lib/common/utils/Objects';
import {Photo} from '../../../../../media/photo/form/photo.interfaces';
import {PhotoDialogComponent} from '../../../../../media/photo/grid/photo-dialog/photo-dialog.component';
import {MeldFormPostComponent} from '../meld-form.classes';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from '../../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-meld-photo-form',
  templateUrl: 'meld-photo-form.component.html',
  styleUrls: ['meld-photo-form.component.css']
})
export class MeldPhotoFormComponent extends MeldFormPostComponent implements OnInit {

  photo: Photo;

  post: MeldPhotoPost;

  constructor(private dialog: MatDialog,
              private http: HttpClient,
              private router : MeldRouterService) {
    super();
  }

  ngOnInit(): void {
    this.post = this.router.data.post;
  }



  open() {
    let matDialogRef = this.dialog.open(PhotoDialogComponent, {width: '400px', height: '400px'});

    matDialogRef.afterClosed().subscribe((result: Photo) => {
      if (Objects.isNotNull(result)) {
        this.photo = result;
        this.post.photoId = result.id;
      }
    });
  }

}
