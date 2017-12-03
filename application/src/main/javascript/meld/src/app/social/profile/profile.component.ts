import {Component} from '@angular/core';
import {MatDialog} from "@angular/material";
import {ImageDialogComponent} from "./image-dialog/image-dialog.component";

@Component({
  selector: 'app-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})
export class ProfileComponent {

  constructor(private dialog : MatDialog) {
  }

  onDialogClick() {
    this.dialog.open(ImageDialogComponent)
  }
}
