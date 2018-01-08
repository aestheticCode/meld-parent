import {School, SchoolDate} from './school-form.interfaces';
import {Place} from 'lib/component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.interfaces';

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
