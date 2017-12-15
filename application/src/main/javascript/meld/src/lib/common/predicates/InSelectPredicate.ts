import {Predicate} from "./Predicate";
import {SubQueryPredicate} from './SubQueryPredicate';

export class InSelectPredicate implements Predicate<SubQueryPredicate>{

  public type: string = "inSelect";

  public value: SubQueryPredicate;

  public path : string;

  constructor(value: SubQueryPredicate, path: string) {
    this.value = value;
    this.path = path;
  }
}
