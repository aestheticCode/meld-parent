import {LinksContainer} from '../../../../../../lib/common/rest/LinksContainer';
import {Link} from '../../../../../../lib/common/rest/Link';

export interface Item extends LinksContainer {

  id: string

  name: string

  time: string;

  category : string;

  text: string;

  avatar: Link;

  likes: any[]

  comments: any[]

  type : string
}
