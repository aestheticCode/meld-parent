import {Education} from "./education.interfaces";
import {School} from "./school-form.interfaces";

export class EducationModel implements Education {
  id: string;
  schools: School[] = [];
}
