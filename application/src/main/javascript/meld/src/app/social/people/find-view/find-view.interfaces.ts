import {BinaryFile} from "../../../../lib/common/rest/BinaryFile";

export interface UserRow {

  id : string

  image : BinaryFile

  name : string

  firstName : string

  lastName : string

}

export interface Category {

  id : string

  name : string

}
