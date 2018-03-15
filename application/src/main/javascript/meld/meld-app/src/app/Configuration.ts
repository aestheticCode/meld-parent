import {LinksContainer} from '@aestheticcode/meld-lib';
import {Link} from "@aestheticcode/meld-lib";
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

    image : Link

  }
}
