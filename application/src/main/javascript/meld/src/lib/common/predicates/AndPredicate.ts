import {Predicate} from "./Predicate";

export class AndPredicate implements Predicate<Predicate<any>[]>{

  public type: string = "and";

  public value: Predicate<any>[];

  constructor(value: Predicate<any>[]) {
    this.value = value;
  }
}
