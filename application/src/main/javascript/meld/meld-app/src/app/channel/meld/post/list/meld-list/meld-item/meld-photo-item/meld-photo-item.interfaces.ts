import {Item} from '../meld-item.interfaces';
import {BinaryFile} from '@aestheticcode/meld-lib';

export interface PhotoItem extends Item {
  photo : BinaryFile;
}
