import {LinksContainer} from '../../../../../lib/common/rest/LinksContainer';

export interface MeldPost extends LinksContainer {
  id: string
  text: string
  type: string
  category : string
}
