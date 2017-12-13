import {Component, ElementRef, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {MeldYouTubePost} from './meld-youtube-form.interfaces';
import {MeldYouTubePostModel} from './meld-youtube-form.classes';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';

@Component({
  selector: 'app-meld-youtube-form',
  templateUrl: 'meld-youtube-form.component.html',
  styleUrls: ['meld-youtube-form.component.css']
})
export class MeldYoutubeFormComponent {

  post: MeldYouTubePost = new MeldYouTubePostModel();

  _url : string;

  youtubeUrl : SafeUrl;

  constructor(private sanitizer: DomSanitizer) {}


  get url(): string {
    return this._url;
  }

  set url(value: string) {
    this._url = value;

    const youtubeRegExp = /(?:[?&]vi?=|\/embed\/|\/\d\d?\/|\/vi?\/|https?:\/\/(?:www\.)?youtu\.be\/)([^&\n?#]+)/;
    const match = this._url.match(youtubeRegExp);

    if( match && match[ 1 ].length == 11 ) {
      this.post.videoId = match[ 1 ];
      this.youtubeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`https://www.youtube.com/embed/${this.post.videoId}?autoplay=0`);
    }

  }


}
