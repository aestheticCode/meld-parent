

import {Predicate} from "./Predicate";

export class OrPredicate implements Predicate<Predicate<any>[]>{

  public type: string = "or";

  public value: Predicate<any>[];

  constructor(value: Predicate<any>[]) {
    this.value = value;
  }
}
