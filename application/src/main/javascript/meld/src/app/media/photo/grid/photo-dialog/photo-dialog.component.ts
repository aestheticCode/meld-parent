import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {MeldGridComponent} from '../../../../../lib/component/meld-grid/meld-grid.component';
import {HttpClient} from '@angular/common/http';
import {Photo} from '../../form/photo.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';
import {AppService} from '../../../../app.service';

@Component({
  selector: 'app-photo-dialog',
  templateUrl: 'photo-dialog.component.html',
  styleUrls: ['photo-dialog.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class PhotoDialogComponent {

  @ViewChild('grid')
  private grid: MeldGridComponent;

  constructor(private http: HttpClient,
              private service : AppService,
              private dialogRef: MatDialogRef<PhotoDialogComponent>) {
  }

  photos: Items<Photo> = (query, callback) => {
    query.expression = QueryBuilder.path("user.id", QueryBuilder.equal(this.service.configuration.user.id));
    this.http.post<Container<Photo>>('service/media/photos/grid', query)
      .subscribe((res) => {
        callback(res.rows, res.size);
      });
  };

  onItemChange(photo: Photo) {
    this.dialogRef.close(photo);
  }

  cancel() {
    this.dialogRef.close();
  }

}
