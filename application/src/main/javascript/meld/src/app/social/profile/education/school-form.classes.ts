import {School} from "./school-form.interfaces";

export class SchoolFormModel implements School {
  tillNow: boolean;
  start: string;
  end: string;
  id: string;
  name: string;
  course: string;
}
