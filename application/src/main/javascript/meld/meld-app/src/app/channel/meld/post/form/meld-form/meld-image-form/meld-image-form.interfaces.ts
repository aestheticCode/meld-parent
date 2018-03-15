import {MeldPost} from '../meld-form.interfaces';
import {BinaryFile} from '@aestheticcode/meld-lib';

export interface MeldImagePost extends MeldPost {
  file: BinaryFile
}
