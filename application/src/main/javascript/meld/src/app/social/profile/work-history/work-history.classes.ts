import {WorkHistory} from "./work-history.interfaces";
import {Company} from './company.interfaces';

export class WorkHistoryModel implements WorkHistory {
  id: string;
  companies: Company[] = [];
}
