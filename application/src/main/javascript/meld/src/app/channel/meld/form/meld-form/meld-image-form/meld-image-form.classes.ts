import {BinaryFile} from '../../../../../../lib/common/rest/BinaryFile';
import {MeldPostModel} from '../meld-form.classes';
import {MeldImagePost} from './meld-image-form.interfaces';

export class MeldImagePostModel extends MeldPostModel implements MeldImagePost {
  type: string = 'image';
  file: BinaryFile;
}
