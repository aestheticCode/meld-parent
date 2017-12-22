import {Query} from './search.classes';

export interface Callback<V> {
  (data : V[], size: number) : void
}

export interface Items<V> {
  (query : Query, response : Callback<V>)
}

export interface SortExpression {
  type : string;
}
