import {LinksContainer} from "../lib/common/rest/LinksContainer";
import {Link} from "../lib/common/rest/Link";
import User = Configuration.User;

export interface Configuration extends LinksContainer {

  user : User

}

export module Configuration {

  export interface User {

    id : string

    firstName : string

    lastName : string

    Birthday : string

    avatar : Link

  }
}
