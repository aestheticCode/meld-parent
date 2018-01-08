import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from 'lib/service/meld-router/meld-router.service';
import {MeldFormPostComponent} from '../meld-form.classes';
import {MeldLinkPost} from './meld-link-form.interfaces';

@Component({
  selector: 'app-meld-link-form',
  templateUrl: 'meld-link-form.component.html',
  styleUrls: ['meld-link-form.component.css']
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
