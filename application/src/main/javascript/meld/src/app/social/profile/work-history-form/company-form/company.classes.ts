import {Company} from "./company.interfaces";

export class CompanyModel implements Company {
  id: string;
  name: string;
  title: string;
  description: string;
  start: string;
  end: string;
}
