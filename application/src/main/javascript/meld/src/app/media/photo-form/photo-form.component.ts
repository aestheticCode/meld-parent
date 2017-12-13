import {Component} from '@angular/core';
import {Photo} from './photo-form.interfaces';
import {PhotoModel} from './photo-form.classes';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-photo-form',
  templateUrl: 'photo-form.component.html',
  styleUrls: ['photo-form.component.css']
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
