import {Component, Inject, OnInit} from '@angular/core';
import {Http, Response} from "@angular/http";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-meld-comment-dialog',
  templateUrl: 'meld-comment-dialog.component.html',
  styleUrls: ['meld-comment-dialog.component.css']
})
export class MeldCommentDialogComponent implements OnInit {

  public comment : any;

  constructor(private http : Http,
              private dialogRef: MatDialogRef<MeldCommentDialogComponent>,
              @Inject(MAT_DIALOG_DATA) private data: any) {
    this.comment = data;
  }

  ngOnInit() {
  }


  plusOneComment(comment) {
    return comment.likes.find((like) => like.current);
  }

  onPlusOneComment() {
    this.http.get(`service/channel/meld/comment/${this.comment.id}/plus/one`)
      .subscribe((res: Response) => {
        this.comment.likes = res.json();
        this.dialogRef.close();
      });
  }

  onEditClick() {
    this.dialogRef.close(true);
  }

}
