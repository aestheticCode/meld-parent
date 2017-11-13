import { Component, OnInit } from '@angular/core';
import {Http, Response} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {AppService} from "../../app.service";
import {Configuration} from "../../Configuration";
import {Post} from "./Post";
import {PostModel} from "./PostModel";

@Component({
  selector: 'app-meld-form',
  templateUrl: './meld-form.component.html',
  styleUrls: ['./meld-form.component.css']
})
export class MeldFormComponent implements OnInit {

  post: Post;

  user: Configuration.User;

  constructor(private http: Http,
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
    this.http.post('service/channel/meld', this.post)
      .subscribe((res: Response) => {
        this.post = res.json();
        this.router.navigate(['channel/meld/posts']);
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
