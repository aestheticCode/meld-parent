import {Component, ViewChild} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {MeldGridComponent} from '../../../../../lib/component/meld-grid/meld-grid.component';
import {HttpClient} from '@angular/common/http';
import {Items} from '../../../../../lib/common/query/Items';
import {Photo} from '../../form/photo.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';

@Component({
  selector: 'app-photo-dialog',
  templateUrl: 'photo-dialog.component.html',
  styleUrls: ['photo-dialog.component.css']
})
export class PhotoDialogComponent {

  @ViewChild('grid')
  private grid: MeldGridComponent;

  constructor(private http: HttpClient,
              private dialogRef: MatDialogRef<PhotoDialogComponent>) {
  }

  photos: Items<Photo> = (query, callback) => {
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
