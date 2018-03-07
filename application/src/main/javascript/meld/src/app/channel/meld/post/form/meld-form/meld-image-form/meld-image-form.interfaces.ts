import {MeldPost} from '../meld-form.interfaces';
import {BinaryFile} from '../../../../../../../lib/common/rest/BinaryFile';

export interface MeldImagePost extends MeldPost {
  file: BinaryFile
}
