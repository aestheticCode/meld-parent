import {LinksContainer} from "../../../lib/common/rest/LinksContainer";
import {BinaryFile} from "../../../lib/common/rest/BinaryFile";

export interface Post extends LinksContainer {
  id : string
  text : string
  file : BinaryFile
}
