import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {MeldGridComponent} from '../../../../../lib/component/meld-grid/meld-grid.component';
import {Photo} from '../grid.interfaces';
import {Container} from '../../../../../lib/common/rest/Container';
import {PhotoFormComponent} from '../../form/photo-form/photo-form.component';
import {Items} from '../../../../../lib/common/search/search.interfaces';
import {QueryBuilder} from '../../../../../lib/common/search/search.classes';
import {AppService} from '../../../../app.service';

@Component({
  selector: 'app-photos-grid',
  templateUrl: 'photos-grid.component.html',
  styleUrls: ['photos-grid.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class PhotosGridComponent {

  selectedPhotos : string[] = [];

  @ViewChild('grid')
  private grid : MeldGridComponent;

  constructor(private http: HttpClient,
              private service : AppService,
              private dialog : MatDialog) {}

  photos: Items<Photo> = (query, callback) => {
    query.expression = QueryBuilder.path("user.id", QueryBuilder.equal(this.service.configuration.user.id));
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
