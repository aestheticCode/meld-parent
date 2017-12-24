import {Place} from '../../../../lib/component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.interfaces';

export interface School {

  id : string

  location : Place

  course : string

  start : SchoolDate

  end : SchoolDate

  tillNow : boolean

}

export interface SchoolDate {

  year : number;

  semester : string

}

