import {Component, Input, ViewEncapsulation} from '@angular/core';
import {TextItem} from './meld-text-item.interfaces';

@Component({
  selector: 'app-meld-text-item',
  templateUrl: 'meld-text-item.component.html',
  styleUrls: ['meld-text-item.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldTextItemComponent {

  @Input('post')
  post: TextItem;

}
