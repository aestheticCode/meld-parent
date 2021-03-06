import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from '@aestheticcode/meld-lib';
import {MeldFormPostComponent} from '../meld-form.classes';
import {MeldLinkPost} from './meld-link-form.interfaces';

@Component({
  selector: 'app-meld-link-form',
  templateUrl: 'meld-link-form.component.html',
  styleUrls: ['meld-link-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldLinkFormComponent extends MeldFormPostComponent implements OnInit {

  public post: MeldLinkPost;

  constructor(private http: HttpClient,
              private router: MeldRouterService) {
    super();
  }

  ngOnInit(): void {
    this.post = this.router.data.post;
  }

}
