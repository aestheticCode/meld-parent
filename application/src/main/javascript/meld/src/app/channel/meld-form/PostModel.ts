import {Post} from "./Post";
import {Link} from "../../../lib/common/rest/Link";
import {BinaryFile} from "../../../lib/common/rest/BinaryFile";

export class PostModel implements Post {
  id: string;
  links: Link[] = [];
  file: BinaryFile;
  text: string = "";

}
