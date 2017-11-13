import {Predicate} from "../predicates/Predicate";

export class Query {

  public index? : number = 0;
  public limit? : number = 0;
  public predicate? : Predicate<any>;

}
