import {Place} from 'lib/component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.interfaces';

export interface Company {

  id : string
  location : Place
  title : string
  description : string
  start : string
  end : string
  tillNow : boolean

}
