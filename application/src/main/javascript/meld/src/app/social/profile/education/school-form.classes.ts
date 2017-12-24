import {School, SchoolDate} from './school-form.interfaces';
import {Place} from 'lib/component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.interfaces';

export class SchoolFormModel implements School {
  location: Place;
  start: SchoolDate = new SchoolDateModel();
  end: SchoolDate = new SchoolDateModel();
  tillNow: boolean;
  id: string;
  course: string;
}

export class SchoolDateModel implements SchoolDate {
  year: number;
  semester: string;
}
