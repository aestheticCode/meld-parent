import {Link} from "../../../../../lib/common/rest/Link";
import {RoleForm} from "./RoleForm";

export class RoleFormModel implements RoleForm {
  id: string;
  name: string;
  permissions: string[];
  links: Link[] = [];
}
