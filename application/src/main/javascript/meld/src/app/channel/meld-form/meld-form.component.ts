import {Component, OnInit, ViewChild} from '@angular/core';
import {Response} from '@angular/http';
import {ActivatedRoute, Router} from '@angular/router';
import {AppService} from '../../app.service';
import {Configuration} from '../../Configuration';
import {MeldPost} from './meld-form.interfaces';
import {HttpClient, HttpEventType, HttpRequest, HttpResponse} from '@angular/common/http';
import {MeldFormPostComponent} from './meld-form.classes';
import {Items} from '../../../lib/common/query/Items';

@Component({
  selector: 'app-meld-form',
  templateUrl: 'meld-form.component.html',
  styleUrls: ['meld-form.component.css']
})
export class MeldFormComponent implements OnInit {

  user: Configuration.User;

  category: string = 'text';

  @ViewChild('form')
  form: MeldFormPostComponent;

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private service: AppService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.data.forEach((data: { post: any, user: any }) => {
      if (data.post) {
        this.category = data.post.type;
        window.setTimeout(() => {
          this.form.post = data.post;
        }, 300);
      }
    });
    this.user = this.service.configuration.user;
  }

  onSave() {

    const request: HttpRequest<MeldPost> = new HttpRequest('POST', 'service/channel/meld', this.form.post, {
      reportProgress: true
    });

    this.http.request(request).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        const percentDone = Math.round(100 * event.loaded / event.total);
        console.log(`File is ${percentDone}% uploaded.`);
      } else if (event instanceof HttpResponse) {
        this.router.navigate(['channel/meld/posts']);
      }
    });

  }

  onUpdate() {
    this.http.put<MeldPost>('service/channel/meld/' + this.form.post.id, this.form.post)
      .subscribe((res: MeldPost) => {
        this.form.post = res;
        this.router.navigate(['channel/meld/posts']);
      });
  }

  onCancel() {
    this.router.navigate(['channel/meld/posts']);
  }

}
