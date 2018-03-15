import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MeldTextPost} from './meld-text-form.interfaces';
import {MeldFormPostComponent} from '../meld-form.classes';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from '@aestheticcode/meld-lib';


@Component({
  selector: 'app-meld-text-form',
  templateUrl: 'meld-text-form.component.html',
  styleUrls: ['meld-text-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldTextFormComponent extends MeldFormPostComponent implements OnInit {

  public post: MeldTextPost;

  constructor(private http: HttpClient,
              private router : MeldRouterService) {
    super();
  }

  ngOnInit(): void {
    this.post = this.router.data.post;
  }


}
