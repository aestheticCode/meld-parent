import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AppService} from '../../../../app.service';
import {Configuration} from '../../../../Configuration';
import {MeldPost} from './meld-form.interfaces';
import {HttpClient} from '@angular/common/http';
import {MeldFormPostComponent} from './meld-form.classes';
import {MatDialog} from '@angular/material';
import {CategoryDialogComponent} from '../../../../social/people/category/category-dialog/category-dialog.component';
import {AbstractForm} from '../../../../../lib/common/forms/AbstractForm';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'app-meld-form',
  templateUrl: 'meld-form.component.html',
  styleUrls: ['meld-form.component.css']
})
export class MeldFormComponent extends AbstractForm<MeldPost> implements OnInit {

  user: Configuration.User;

  category: string = 'text';

  @Input('post')
  post : MeldPost;

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private service: AppService,
              private dialog: MatDialog,
              private router: Router) {
    super();
  }

  ngOnInit(): void {
    this.user = this.service.configuration.user;
  }

  open(post: MeldPost) {
    this.dialog.open(CategoryDialogComponent, {data: post});
  }

  saveRequest(): Observable<MeldPost> {
    return this.http.post<MeldPost>(`service/channel/meld`, this.post);
  }

  updateRequest(): Observable<MeldPost> {
    return this.http.put<MeldPost>(`service/channel/meld/${this.post.id}`, this.post);
  }

  deleteRequest(): Observable<MeldPost> {
    return this.http.delete<MeldPost>(`service/channel/meld/${this.post.id}`);
  }

  public postRequest(form: MeldPost) {
    this.router.navigate(['channel/meld/posts']);
  }
}
