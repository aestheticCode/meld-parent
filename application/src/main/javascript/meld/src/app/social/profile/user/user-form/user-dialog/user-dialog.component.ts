import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {UserForm} from '../../user.interfaces';

@Component({
  selector: 'app-user-dialog',
  templateUrl: './user-dialog.component.html',
  styleUrls: ['./user-dialog.component.css']
})
export class UserDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: UserForm,
              public dialogRef: MatDialogRef<UserDialogComponent>) {
  }

  onSave() {
    this.dialogRef.close(this.data);
  }

  onCancel() {
    this.dialogRef.close();
  }

}
