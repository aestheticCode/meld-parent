import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Photo} from '../photo.interfaces';

@Component({
  selector: 'app-photo-view',
  templateUrl: 'photo-view.component.html',
  styleUrls: ['photo-view.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class PhotoViewComponent implements OnInit {

  @Input('id')
  id: string;

  photo : Photo;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.http.get<Photo>(`service/media/photo/${this.id}`)
      .subscribe((photo => {
        this.photo = photo;
      }));
  }

}
