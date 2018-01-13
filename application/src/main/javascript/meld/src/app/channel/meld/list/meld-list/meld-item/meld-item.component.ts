import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Http, Response} from "@angular/http";
import {AppService} from "../../../../../app.service";
import {Item} from './meld-item.interfaces';

@Component({
  selector: 'app-meld-item',
  templateUrl: 'meld-item.component.html',
  styleUrls: ['meld-item.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldItemComponent implements OnInit {

  @Input("post")
  public post : Item;

  public user : any;

  public commentsVisible : boolean = false;

  public comment : string = "";

  @Output("selectionChange")
  private selectionChange : EventEmitter<Item> = new EventEmitter();

  constructor(private http : Http,
              private service : AppService) {
    this.user = this.service.configuration.user;
  }

  ngOnInit() {
  }

  onPlusOneClick() {
    this.http.get(`service/channel/meld/${this.post.id}/plus/one`)
      .subscribe((res: Response) => {
        this. post.likes = res.json();
      });
  }


  plusOneButtonActive() {
    return this.post.likes.find((like) => like.current);
  }

  onCommentPost() {
    this.http.post(`service/channel/meld/post/${this.post.id}/comment`, { text : this.comment })
      .subscribe((res: Response) => {
        this.post.comments.push(res.json());
        this.comment = "";
      });
  }

  onPostClick() {
    this.selectionChange.emit(this.post);
  }

}
