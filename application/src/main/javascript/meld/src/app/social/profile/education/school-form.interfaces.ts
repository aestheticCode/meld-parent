import {Place} from '../../../../lib/component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.interfaces';

export interface School {

  id : string

  name : string;

  location : Place;

  course : string

  startYear : SchoolDate

  endYear : SchoolDate

  visitStart : SchoolDate

  visitEnd : SchoolDate

  tillNow : boolean

}

export interface SchoolDate {

  year : number;

  semester : string

}

