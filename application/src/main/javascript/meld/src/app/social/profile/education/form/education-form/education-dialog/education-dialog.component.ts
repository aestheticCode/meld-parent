import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-social-education-dialog',
  templateUrl: 'education-dialog.component.html',
  styleUrls: ['education-dialog.component.css']
})
export class EducationDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: FormControl,
              public dialogRef: MatDialogRef<EducationDialogComponent>) {
  }

  onSave() {
    this.dialogRef.close(this.data);
  }

  onCancel() {
    this.dialogRef.close();
  }

}
