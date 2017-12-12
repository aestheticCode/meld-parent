import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-photo-view',
  templateUrl: 'photo-view.component.html',
  styleUrls: ['photo-view.component.css']
})
export class PhotoViewComponent implements OnInit {

  @Input('photo')
  photo : any;

  constructor() { }

  ngOnInit() {
  }

}
