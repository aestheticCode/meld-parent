import {Predicate} from "./Predicate";

export class EqualPredicate implements Predicate<any> {

  value: any;
  path: string;
  type: string = "equal";


  constructor(value: any, path: string) {
    this.value = value;
    this.path = path;
  }
}

