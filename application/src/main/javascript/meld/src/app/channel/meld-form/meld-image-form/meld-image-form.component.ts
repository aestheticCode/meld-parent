import {Component} from '@angular/core';
import {MeldImagePost} from './meld-image-form.interfaces';
import {MeldImagePostModel} from './meld-image-form.classes';
import {MeldFormPostComponent} from '../meld-form.classes';

@Component({
  selector: 'app-meld-image-form',
  templateUrl: 'meld-image-form.component.html',
  styleUrls: ['meld-image-form.component.css']
})
export class MeldImageFormComponent extends MeldFormPostComponent {

  public post: MeldImagePost = new MeldImagePostModel();

}
