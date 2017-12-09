import {Component} from '@angular/core';
import {MeldTextPostModel} from './meld-text-form.classes';
import {MeldTextPost} from './meld-text-form.interfaces';
import {MeldFormPostComponent} from '../meld-form.classes';


@Component({
  selector: 'app-meld-text-form',
  templateUrl: 'meld-text-form.component.html',
  styleUrls: ['meld-text-form.component.css']
})
export class MeldTextFormComponent extends MeldFormPostComponent {

  public post: MeldTextPost = new MeldTextPostModel();

}
