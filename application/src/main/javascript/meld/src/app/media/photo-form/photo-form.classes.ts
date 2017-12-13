import {Photo} from './photo-form.interfaces';
import {BinaryFile} from '../../../lib/common/rest/BinaryFile';

export class PhotoModel implements Photo {
  id: string = null;
  image: BinaryFile = null;

}
