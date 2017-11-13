import {Component, Input, OnInit} from '@angular/core';
import {MdDialog} from "@angular/material";
import {Http, Response} from "@angular/http";
import {MeldCommentDialogComponent} from "./meld-comment-dialog/meld-comment-dialog.component";

@Component({
  selector: 'app-meld-comment',
  templateUrl: 'meld-comment.component.html',
  styleUrls: ['meld-comment.component.css']
})
export class MeldCommentComponent implements OnInit {

  public edit : boolean;

  @Input("comment")
  public comment : any;

  constructor(private http : Http,
              private dialog: MdDialog) { }

  ngOnInit() {
  }

  onClick() {
    this.dialog
      .open(MeldCommentDialogComponent, {disableClose : false, data : this.comment})
      .afterClosed()
      .subscribe((result) => {
        this.edit = result;
      })
  }

  onCancelClick(event : MouseEvent) {
    this.edit = false;
    event.stopPropagation();
    return false;
  }

  onUpdateClick() {
    this.http.put('service/channel/meld/post/comment/' + this.comment.id, this.comment)
      .subscribe((res: Response) => {
        this.edit = false;
      });
  }


}
