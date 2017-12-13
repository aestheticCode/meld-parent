import {MeldPhotoPost} from './meld-photo-form.interfaces';
import {MeldPostModel} from '../meld-form.classes';

export class MeldPhotoPostModel extends MeldPostModel implements MeldPhotoPost {
  type : string = "photo";
  photoId: string = null;
}
