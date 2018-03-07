import {Component, Input, ViewEncapsulation} from '@angular/core';
import {ImageItem} from './meld-image-item.interfaces';

@Component({
  selector: 'app-meld-image-item',
  templateUrl: 'meld-image-item.component.html',
  styleUrls: ['meld-image-item.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldImageItemComponent {

  @Input('post')
  post: ImageItem;

}
