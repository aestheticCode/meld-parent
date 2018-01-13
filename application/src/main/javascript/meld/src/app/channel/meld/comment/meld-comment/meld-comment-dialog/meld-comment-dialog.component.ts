import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {Http, Response} from "@angular/http";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-meld-comment-dialog',
  templateUrl: 'meld-comment-dialog.component.html',
  styleUrls: ['meld-comment-dialog.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldCommentDialogComponent {

  public comment : any;

  constructor(private http : HttpClient,
              private dialogRef: MatDialogRef<MeldCommentDialogComponent>,
              @Inject(MAT_DIALOG_DATA) private data: any) {
    this.comment = data;
  }


  plusOneComment(comment) {
    return comment.likes.find((like) => like.current);
  }

  onPlusOneComment() {
    this.http.get(`service/channel/meld/comment/${this.comment.id}/plus/one`)
      .subscribe((res: any) => {
        this.comment.likes = res;
        this.dialogRef.close();
      });
  }

  onDeleteClick() {
    this.http.delete(`service/channel/meld/post/comment/${this.comment.id}`)
      .subscribe(res => {
        this.dialogRef.close(false);
      });
  }

  onEditClick() {
    this.dialogRef.close(true);
  }

}
