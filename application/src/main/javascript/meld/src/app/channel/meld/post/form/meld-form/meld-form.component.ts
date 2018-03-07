import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MeldPost} from './meld-form.interfaces';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {Observable} from 'rxjs/Observable';
import {AbstractForm} from '../../../../../../lib/common/forms/AbstractForm';
import {Configuration} from '../../../../../Configuration';
import {AppService} from '../../../../../app.service';
import {CategorySelectDialogComponent} from '../../../../../social/people/category/select/category-select-dialog/category-select-dialog.component';

@Component({
  selector: 'app-meld-form',
  templateUrl: 'meld-form.component.html',
  styleUrls: ['meld-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MeldFormComponent extends AbstractForm<MeldPost> implements OnInit {

  user: Configuration.User;

  category: string = 'text';

  @Input('post')
  post: MeldPost;

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
    this.dialog.open(CategorySelectDialogComponent, {data: post});
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
