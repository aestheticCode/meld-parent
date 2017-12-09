import {MeldPost} from './meld-form.interfaces';
import {Link} from '../../../lib/common/rest/Link';

export class MeldFormPostComponent {
  public post: MeldPost;
}

export abstract class MeldPostModel implements MeldPost {
  type: string;
  id: string = null;
  text: string = null;
  links: Link[] = [];
}
