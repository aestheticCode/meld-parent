import {Component, Input} from '@angular/core';
import {LinkItem} from './meld-link-item.interfaces';

@Component({
  selector: 'app-meld-link-item',
  templateUrl: 'meld-link-item.component.html',
  styleUrls: ['meld-link-item.component.css']
})
export class MeldLinkItemComponent {

  @Input('post')
  post: LinkItem;

}
