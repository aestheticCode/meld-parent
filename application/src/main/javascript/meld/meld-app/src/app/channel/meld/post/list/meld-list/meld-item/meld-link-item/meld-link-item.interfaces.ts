import {Item} from '../meld-item.interfaces';
import {BinaryFile} from '@aestheticcode/meld-lib';

export interface LinkItem extends Item {

  link : string;

  image : BinaryFile;

}
