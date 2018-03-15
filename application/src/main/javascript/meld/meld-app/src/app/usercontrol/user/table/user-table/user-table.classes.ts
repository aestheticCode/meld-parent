import {FilterTemplate} from "./user-table.interfaces";

export class FilterTemplateModel implements FilterTemplate {
  name: string;
  firstName: string;
  lastName: string;
  birthdate: Date;
}
