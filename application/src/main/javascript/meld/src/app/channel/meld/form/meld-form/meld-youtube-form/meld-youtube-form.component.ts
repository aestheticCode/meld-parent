import {Component} from '@angular/core';
import {MeldYouTubePost} from './meld-youtube-form.interfaces';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {HttpClient} from '@angular/common/http';
import {MeldRouterService} from '../../../../../../lib/service/meld-router/meld-router.service';

@Component({
  selector: 'app-meld-youtube-form',
  templateUrl: 'meld-youtube-form.component.html',
  styleUrls: ['meld-youtube-form.component.css']
})
export class MeldYoutubeFormComponent {

  post: MeldYouTubePost;

  _url: string;

  youtubeUrl: SafeUrl;

  constructor(private sanitizer: DomSanitizer,
              private http: HttpClient,
              private router : MeldRouterService) {
  }

  ngOnInit(): void {
    this.post = this.router.data.post;
  }



  get url(): string {
    return this._url;
  }

  set url(value: string) {
    this._url = value;

    const youtubeRegExp = /(?:[?&]vi?=|\/embed\/|\/\d\d?\/|\/vi?\/|https?:\/\/(?:www\.)?youtu\.be\/)([^&\n?#]+)/;
    const match = this._url.match(youtubeRegExp);

    if (match && match[1].length == 11) {
      this.post.videoId = match[1];
      this.youtubeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`https://www.youtube.com/embed/${this.post.videoId}?autoplay=0`);
    }

  }

}
