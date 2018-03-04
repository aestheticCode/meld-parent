import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-work-history-dialog',
  templateUrl: './work-history-dialog.component.html',
  styleUrls: ['./work-history-dialog.component.css']
})
export class WorkHistoryDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: FormControl,
              public dialogRef: MatDialogRef<WorkHistoryDialogComponent>) {
  }

  onSave() {
    this.dialogRef.close(this.data);
  }

  onCancel() {
    this.dialogRef.close();
  }

}
