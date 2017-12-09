import {BinaryFile} from '../../../../lib/common/rest/BinaryFile';
import {MeldPost} from '../meld-form.interfaces';

export interface MeldImagePost extends MeldPost {
  file: BinaryFile
}
