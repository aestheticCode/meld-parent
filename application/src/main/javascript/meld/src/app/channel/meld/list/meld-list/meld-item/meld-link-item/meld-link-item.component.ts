import {Component, Input, ViewEncapsulation} from '@angular/core';
import {LinkItem} from './meld-link-item.interfaces';

@Component({
  selector: 'app-meld-link-item',
  templateUrl: 'meld-link-item.component.html',
  styleUrls: ['meld-link-item.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldLinkItemComponent {

  @Input('post')
  post: LinkItem;

}
