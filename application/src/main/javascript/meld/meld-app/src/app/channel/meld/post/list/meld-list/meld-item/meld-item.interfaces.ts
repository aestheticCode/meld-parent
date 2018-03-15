import {LinksContainer} from '@aestheticcode/meld-lib';
import {Link} from '@aestheticcode/meld-lib';

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
