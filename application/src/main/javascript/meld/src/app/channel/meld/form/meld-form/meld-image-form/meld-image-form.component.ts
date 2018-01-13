import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MeldImagePost} from './meld-image-form.interfaces';
import {MeldFormPostComponent} from '../meld-form.classes';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from '../../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-meld-image-form',
  templateUrl: 'meld-image-form.component.html',
  styleUrls: ['meld-image-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldImageFormComponent extends MeldFormPostComponent implements OnInit {

  public post: MeldImagePost;

  constructor(private http: HttpClient,
              private router : MeldRouterService) {
    super();
  }

  ngOnInit(): void {
    this.post = this.router.data.post;
  }

}
