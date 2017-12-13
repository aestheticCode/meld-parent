import {Component} from '@angular/core';
import {MeldPhotoPost} from './meld-photo-form.interfaces';
import {MeldPhotoPostModel} from './meld-photo-form.classes';
import {MatDialog} from '@angular/material';
import {Objects} from '../../../../../../lib/common/utils/Objects';
import {Photo} from '../../../../../media/photo/form/photo.interfaces';
import {PhotoDialogComponent} from '../../../../../media/photo/grid/photo-dialog/photo-dialog.component';

@Component({
  selector: 'app-meld-photo-form',
  templateUrl: 'meld-photo-form.component.html',
  styleUrls: ['meld-photo-form.component.css']
})
export class MeldPhotoFormComponent {

  photo : Photo;

  post: MeldPhotoPost = new MeldPhotoPostModel();

  constructor(private dialog : MatDialog) {}

  open() {
    let matDialogRef = this.dialog.open(PhotoDialogComponent, {width : '400px', height : '400px'});

    matDialogRef.afterClosed().subscribe((result : Photo) => {
      if (Objects.isNotNull(result)) {
        this.photo = result;
        this.post.photoId = result.id;
      }
    })
  }

}
