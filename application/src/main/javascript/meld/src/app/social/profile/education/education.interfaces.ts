import {School} from "./school-form.interfaces";
import {LinksContainer} from 'lib/common/rest/LinksContainer';

export interface Education extends LinksContainer {

  id : string

  schools : School[]

}
