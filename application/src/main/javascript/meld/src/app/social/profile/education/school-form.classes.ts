import {School, SchoolDate} from './school-form.interfaces';

export class SchoolFormModel implements School {
  start: SchoolDate = new SchoolDateModel();
  end: SchoolDate = new SchoolDateModel();
  tillNow: boolean;
  id: string;
  name: string;
  course: string;
}

export class SchoolDateModel implements SchoolDate {
  year: number;
  semester: string;
}
