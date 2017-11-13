import {Callback} from "./Callback";
import {Query} from "./Query";

export interface Items<V> {
  (query : Query, response : Callback<V>)
}
