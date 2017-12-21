import {LinksContainer} from "lib/common/rest/LinksContainer";

export interface RegistrationForm extends LinksContainer{

  firstName : string

  lastName : string

  birthday : string

  password : string

  confirm : string

}
