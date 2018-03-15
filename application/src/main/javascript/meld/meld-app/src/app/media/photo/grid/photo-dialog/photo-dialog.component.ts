import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {MeldGridComponent} from '@aestheticcode/meld-lib';
import {HttpClient} from '@angular/common/http';
import {Photo} from '../../form/photo.interfaces';
import {Container} from '@aestheticcode/meld-lib';
import {Items} from '@aestheticcode/meld-lib';
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

    const params = {
      index : query.index.toString(),
      limit : query.limit.toString()
    };

    this.http.get<Container<Photo>>('service/media/photos/grid', {params : params})
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
