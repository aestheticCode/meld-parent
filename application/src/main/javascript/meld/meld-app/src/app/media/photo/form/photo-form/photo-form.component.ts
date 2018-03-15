import {Component, ViewEncapsulation} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {Photo} from '../photo.interfaces';
import {PhotoModel} from '../photo.classes';

@Component({
  selector: 'app-photo-form',
  templateUrl: 'photo-form.component.html',
  styleUrls: ['photo-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class PhotoFormComponent {

  public photo: Photo = new PhotoModel();

  constructor(private dialogRef: MatDialogRef<PhotoFormComponent>) {
  }

  close() {
    this.dialogRef.close();
  }

  save() {
    this.dialogRef.close(this.photo);
  }
}
