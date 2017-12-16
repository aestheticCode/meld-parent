import {Component, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {MeldGridComponent} from '../../../../../lib/component/meld-grid/meld-grid.component';
import {Items} from '../../../../../lib/common/query/Items';
import {Photo} from '../grid.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {PhotoFormComponent} from '../../form/photo-form/photo-form.component';

@Component({
  selector: 'app-photos-grid',
  templateUrl: 'photos-grid.component.html',
  styleUrls: ['photos-grid.component.css']
})
export class PhotosGridComponent {

  selectedPhotos : string[] = [];

  @ViewChild('grid')
  private grid : MeldGridComponent;

  constructor(private http: HttpClient,
              private dialog : MatDialog) {}

  photos: Items<Photo> = (query, callback) => {
    this.http.post<Container<Photo>>('service/media/photos/grid', query)
      .subscribe((res) => {
        callback(res.rows, res.size);
      });
  };

  upload() {
    let dialogRef = this.dialog.open(PhotoFormComponent, {width : '350px'});
    dialogRef.afterClosed().subscribe((photo) => {
      this.http.post<Container<Photo>>('service/media/photo', photo)
        .subscribe((res) => {
          this.grid.refreshItems();
        });
    });
  }

  delete() {
    const ids = this.selectedPhotos.map((id) => 'id=' + id).join("&");
    this.http.delete<void>(`service/media/photo?${ids}`)
      .subscribe((res) => {
        this.grid.refreshItems();
        this.selectedPhotos = [];
      });
  }

}
