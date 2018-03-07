import {Item} from '../meld-item.interfaces';
import {BinaryFile} from '../../../../../../../../lib/common/rest/BinaryFile';

export interface PhotoItem extends Item {
  photo : BinaryFile;
}
