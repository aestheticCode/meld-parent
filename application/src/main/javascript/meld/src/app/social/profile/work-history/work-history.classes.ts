import {WorkHistory} from "./work-history.interfaces";
import {Company} from './company.interfaces';
import {Link} from '../../../../lib/common/rest/Link';

export class WorkHistoryModel implements WorkHistory {
  links: Link[] = [];
  id: string;
  companies: Company[] = [];
}
