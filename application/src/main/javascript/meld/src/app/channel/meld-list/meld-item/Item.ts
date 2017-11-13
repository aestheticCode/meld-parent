import {LinksContainer} from "../../../../lib/common/rest/LinksContainer";

export interface Item extends LinksContainer {

  id : string

  name : string

  time : string;

  text : string;

  image : string;

  likes : any[]

  comments : any[]
}
