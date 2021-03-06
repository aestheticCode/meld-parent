import {Component, Input, ViewEncapsulation} from '@angular/core';
import {PhotoItem} from './meld-photo-item.interfaces';

@Component({
  selector: 'app-meld-photo-item',
  templateUrl: 'meld-photo-item.component.html',
  styleUrls: ['meld-photo-item.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldPhotoItemComponent {

  @Input('post')
  post: PhotoItem;

}
