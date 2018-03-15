import {School, SchoolDate} from './school-form.interfaces';
import {Place} from '@aestheticcode/meld-lib';

export class SchoolFormModel implements School {
  id: string;
  name: string;
  location: Place;
  startYear: SchoolDate = new SchoolDateModel();
  endYear: SchoolDate = new SchoolDateModel();
  visitStart: SchoolDate = new SchoolDateModel();
  visitEnd: SchoolDate = new SchoolDateModel();
  tillNow: boolean;
  course: string;
}

export class SchoolDateModel implements SchoolDate {
  year: number;
  semester: string;
}
