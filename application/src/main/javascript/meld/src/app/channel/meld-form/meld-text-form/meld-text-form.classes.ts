import {MeldPostModel} from '../meld-form.classes';
import {MeldTextPost} from './meld-text-form.interfaces';

export class MeldTextPostModel extends MeldPostModel implements MeldTextPost {
  type: string = 'text';
}

