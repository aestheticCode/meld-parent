import {Place} from '@aestheticcode/meld-lib';

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

