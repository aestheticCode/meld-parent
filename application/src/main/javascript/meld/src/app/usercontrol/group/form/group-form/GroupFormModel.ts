import {GroupForm} from "./GroupForm";
import {Link} from '../../../../../lib/common/rest/Link';

export class GroupFormModel implements GroupForm {
  id: string;
  name: string;
  roles: string[];
  links: Link[] = [];
}
