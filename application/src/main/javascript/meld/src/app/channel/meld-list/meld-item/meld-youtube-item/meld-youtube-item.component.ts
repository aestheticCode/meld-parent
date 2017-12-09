import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {YouTubeItem} from './meld-youtube-item.interfaces';

@Component({
  selector: 'app-meld-youtube-item',
  templateUrl: 'meld-youtube-item.component.html',
  styleUrls: ['meld-youtube-item.component.css']
})
export class MeldYoutubeItemComponent implements OnChanges {

  @Input('post')
  post: YouTubeItem;

  url : SafeUrl;

  constructor(private sanitizer: DomSanitizer) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['post']) {
      this.url = this.sanitizer.bypassSecurityTrustResourceUrl(`https://www.youtube.com/embed/${this.post.videoId}?autoplay=0`);
    }
  }

}
