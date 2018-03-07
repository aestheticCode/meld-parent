import {Item} from '../meld-item.interfaces';
import {BinaryFile} from '../../../../../../../../lib/common/rest/BinaryFile';

export interface LinkItem extends Item {

  link : string;

  image : BinaryFile;

}
