import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MeldPhotoPost} from './meld-photo-form.interfaces';
import {MatDialog} from '@angular/material';
import {MeldFormPostComponent} from '../meld-form.classes';
import {HttpClient} from '@angular/common/http';
import {Photo} from '../../../../../../media/photo/form/photo.interfaces';
import {MeldRouterService} from '@aestheticcode/meld-lib';
import {PhotoDialogComponent} from '../../../../../../media/photo/grid/photo-dialog/photo-dialog.component';
import {Objects} from '@aestheticcode/meld-lib';

@Component({
  selector: 'app-meld-photo-form',
  templateUrl: 'meld-photo-form.component.html',
  styleUrls: ['meld-photo-form.component.css'],
  encapsulation : ViewEncapsulation.None
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
