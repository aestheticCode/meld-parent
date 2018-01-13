import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialogRef} from "@angular/material";
import {Http, Response} from "@angular/http";
import {MeldListComponent} from "../../../../../../lib/component/meld-list/meld-list.component";
import {UserRow} from '../../../../../usercontrol/user/table/user-table/user-table.interfaces';
import {Items} from '../../../../../../lib/common/search/search.interfaces';
import {QueryBuilder} from '../../../../../../lib/common/search/search.classes';

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
    let query = QueryBuilder.query();
    query.limit = 75;
    query.expression = QueryBuilder.path("name", QueryBuilder.like(this.filter));
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
