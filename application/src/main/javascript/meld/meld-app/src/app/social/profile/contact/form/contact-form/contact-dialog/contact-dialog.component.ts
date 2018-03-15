import {Component, Inject} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-contact-dialog',
  templateUrl: 'contact-dialog.component.html',
  styleUrls: ['contact-dialog.component.css']
})
export class ContactDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: FormControl,
              public dialogRef: MatDialogRef<ContactDialogComponent>) {
  }

  onSave() {
    this.dialogRef.close(this.data);
  }

  onCancel() {
    this.dialogRef.close();
  }

}
