import {Predicate} from "../predicates/Predicate";
import {Sort} from './Sort';

export class Query {

  public index : number = 0;
  public limit : number = 0;
  public predicate : Predicate<any>;
  public sorting : Sort[];

}
