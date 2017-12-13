import { Component, OnInit } from '@angular/core';
import {BinaryFile} from '../../../../../lib/common/rest/BinaryFile';

@Component({
  selector: 'app-image-dialog',
  templateUrl: 'image-dialog.component.html',
  styleUrls: ['image-dialog.component.css']
})
export class ImageDialogComponent implements OnInit {

  image : BinaryFile;

  constructor() { }

  ngOnInit() {
  }

}
