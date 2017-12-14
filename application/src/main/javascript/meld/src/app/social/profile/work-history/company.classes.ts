import {Company} from "./company.interfaces";

export class CompanyModel implements Company {
  tillNow: boolean;
  id: string;
  name: string;
  title: string;
  description: string;
  start: string;
  end: string;
}
