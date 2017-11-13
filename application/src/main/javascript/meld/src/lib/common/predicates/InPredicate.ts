import {Predicate} from "./Predicate";

export class InPredicate implements Predicate<any[]>{

  public type: string = "in";

  public value: any[];

  public path : string;

  constructor(value: any[], path: string) {
    this.value = value;
    this.path = path;
  }
}
