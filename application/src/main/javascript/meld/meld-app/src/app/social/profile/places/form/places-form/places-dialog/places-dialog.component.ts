import {Component, Inject} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-places-dialog',
  templateUrl: 'places-dialog.component.html',
  styleUrls: ['places-dialog.component.css']
})
export class PlacesDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: FormControl,
              public dialogRef: MatDialogRef<PlacesDialogComponent>) {
  }

  onSave() {
    this.dialogRef.close(this.data);
  }

  onCancel() {
    this.dialogRef.close();
  }

}
