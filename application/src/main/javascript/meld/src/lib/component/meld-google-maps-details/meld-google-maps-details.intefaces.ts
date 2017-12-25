export interface MeldGoogleMapsDetails {

  website: string;
  url: string;
  utcOffset: string;
  formatted_address: string;
  international_phoneNumber: string;
  formatted_phone_number: string;
  geometry : GoogleGeometry,
  address_components: Map<string, string>;

}

export interface GoogleGeometry {

  location : GoogleLocation

}

export interface GoogleLocation {

  lat : number;
  lng : number;

}
