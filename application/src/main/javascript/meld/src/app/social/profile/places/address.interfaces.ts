import {Place} from '../../../../lib/component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.interfaces';

export interface Address {

  id : string;

  location : Place

  start : string

  end : string

  tillNow : boolean

}
