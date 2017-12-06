import { Component, OnInit } from '@angular/core';
import {Http, Response} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {AppService} from "../../app.service";
import {Configuration} from "../../Configuration";
import {Post} from "./Post";
import {PostModel} from "./PostModel";
import {HttpClient, HttpEventType, HttpHeaders, HttpRequest, HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-meld-form',
  templateUrl: './meld-form.component.html',
  styleUrls: ['./meld-form.component.css']
})
export class MeldFormComponent implements OnInit {

  post: Post;

  user: Configuration.User;

  constructor(private http : HttpClient,
              private route: ActivatedRoute,
              private service: AppService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.data.forEach((data: { post: any, user: any }) => {
      this.post = data.post || new PostModel();
    });
    this.user = this.service.configuration.user;
  }

  onSave() {

    const request : HttpRequest<Post> = new HttpRequest('POST', 'service/channel/meld', this.post, {
      reportProgress: true
    });

    this.http.request(request).subscribe(event => {
      // Via this API, you get access to the raw event stream.
      // Look for upload progress events.
      if (event.type === HttpEventType.UploadProgress) {
        // This is an upload progress event. Compute and show the % done:
        const percentDone = Math.round(100 * event.loaded / event.total);
        console.log(`File is ${percentDone}% uploaded.`);
      } else if (event instanceof HttpResponse) {
        this.router.navigate(['channel/meld/posts']);
      }
    });

  }

  onUpdate() {
    this.http.put('service/channel/meld/' + this.post.id, this.post)
      .subscribe((res: Response) => {
        this.post = res.json();
        this.router.navigate(['channel/meld/posts']);
      });
  }

  onCancel() {
    this.router.navigate(['channel/meld/posts']);
  }


  onImageRemove() {
    this.post.file = null
  }

}
