import {MeldPostModel} from '../meld-form.classes';
import {MeldYouTubePost} from './meld-youtube-form.interfaces';

export class MeldYouTubePostModel extends MeldPostModel implements MeldYouTubePost {
  type: string = 'youtube';
  videoId : string = null;
}

