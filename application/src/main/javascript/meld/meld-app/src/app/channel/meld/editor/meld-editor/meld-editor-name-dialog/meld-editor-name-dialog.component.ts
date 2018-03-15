import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialogRef} from "@angular/material";
import {Http, Response} from "@angular/http";
import {MeldListComponent} from '@aestheticcode/meld-lib';
import {UserRow} from '../../../../../usercontrol/user/table/user-table/user-table.interfaces';
import {Items, Query} from '@aestheticcode/meld-lib';

@Component({
  selector: 'app-meld-editor-name-dialog',
  templateUrl: 'meld-editor-name-dialog.component.html',
  styleUrls: ['meld-editor-name-dialog.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class MeldEditorNameDialogComponent {

  private filter: string;

  @ViewChild("list")
  private list: MeldListComponent;

  constructor(private http: Http,
              private dialogRef: MatDialogRef<MeldEditorNameDialogComponent>) {
  }

  users: Items<any> = (params, callback) => {
    let query = new Query()
    query.limit = 75;
    this.http.post('service/usercontrol/user/table', query)
      .subscribe((res: Response) => {
        let rows: UserRow[] = res.json().rows;
        callback(rows, null);
      });
  };

  select(user: UserRow) {
    this.dialogRef.close(user);
  }

  doFilter(context: string) {
    this.filter = context;
    this.list.refresh();
  }
}
