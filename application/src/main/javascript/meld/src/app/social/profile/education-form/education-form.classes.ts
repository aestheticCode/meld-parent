import {Education} from "./education-form.interfaces";
import {School} from "./school-form/school-form.interfaces";

export class EducationModel implements Education {
  id: string;
  schools: School[] = [];
}
