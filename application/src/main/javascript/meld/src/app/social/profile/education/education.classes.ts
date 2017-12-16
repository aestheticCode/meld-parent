import {Education} from "./education.interfaces";
import {School} from "./school-form.interfaces";
import {Link} from '../../../../lib/common/rest/Link';

export class EducationModel implements Education {
  links: Link[];
  id: string;
  schools: School[] = [];
}
