import {Query} from './search.classes';
import {RestExpression} from './expression.interfaces';

export interface Callback<V> {
  (data : V[], size: number) : void
}

export interface Items<V> {
  (query : Query, response : Callback<V>)
}

export interface SortExpression {
  type : string;
}

export interface Filter {

  name : string;
  expression : RestExpression
  active : boolean;

}
