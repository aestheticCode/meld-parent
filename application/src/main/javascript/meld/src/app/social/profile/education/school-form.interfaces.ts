export interface School {

  id : string

  name : string

  course : string

  start : SchoolDate

  end : SchoolDate

  tillNow : boolean

}

export interface SchoolDate {

  year : number;

  semester : string

}

