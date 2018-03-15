import {Photo} from './photo.interfaces';
import {BinaryFile} from '@aestheticcode/meld-lib';

export class PhotoModel implements Photo {
  id: string = null;
  image: BinaryFile = null;

}
