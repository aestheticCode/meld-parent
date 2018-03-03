import {MeldPost} from './meld-form.interfaces';
import {Link} from '../../../../../lib/common/rest/Link';
import {Category} from '../../../../social/people/category/categories.interfaces';

export class MeldFormPostComponent {
  public post: MeldPost;
}

export abstract class MeldPostModel implements MeldPost {
  category: Category = null;
  type: string;
  id: string = null;
  text: string = null;
  links: Link[] = [];
}
