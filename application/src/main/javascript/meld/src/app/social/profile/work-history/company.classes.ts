import {Company} from "./company.interfaces";
import {Place} from '../../../../lib/component/meld-google-maps-autocomplete/meld-google-maps-autocomplete.interfaces';

export class CompanyModel implements Company {
  tillNow: boolean;
  id: string;
  location: Place;
  title: string;
  description: string;
  start: string;
  end: string;
}
