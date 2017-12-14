import {Component, ContentChild, Input, TemplateRef} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material';

@Component({
  selector: 'meld-dialog',
  templateUrl: 'meld-dialog.component.html',
  styleUrls: ['meld-dialog.component.css']
})
export class MeldDialogComponent {

  @ContentChild(TemplateRef)
  public template: TemplateRef<any>;

  @Input('options')
  public options: MatDialogConfig = {};

  private dialogRef;

  constructor(private dialog: MatDialog) {
  }

  open() {
    if (this.dialogRef) {
      return;
    }
    this.dialogRef = this.dialog.open(this.template, this.options);

    this.dialogRef.afterClosed().subscribe((result) => {
      this.dialogRef = null;
    })
  }

  close() {
    if (this.dialogRef) {
      this.dialogRef.close();
      this.dialogRef = null;
    }
  }

}
