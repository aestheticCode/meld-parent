import {Component, Input} from '@angular/core';
import {Photo} from './photo-view.interfaces';

@Component({
  selector: 'app-photo-view',
  templateUrl: 'photo-view.component.html',
  styleUrls: ['photo-view.component.css']
})
export class PhotoViewComponent {

  @Input('photo')
  photo: Photo;

}
