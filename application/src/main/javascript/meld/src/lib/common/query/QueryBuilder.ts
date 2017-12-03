import {Predicate} from "../predicates/Predicate";
import {Query} from "./Query";
import {AndPredicate} from "../predicates/AndPredicate";
import {OrPredicate} from "../predicates/OrPedicate";
import {LikePredicate, LikeType} from "../predicates/LikePredicate";
import {InPredicate} from "../predicates/InPredicate";
import {EqualPredicate} from "../predicates/EqualPredicate";
import {DatePredicate, DateType} from "../predicates/DatePredicate";

export class QueryBuilder {

  public static query() {
    return new Query();
  }

  public static equal(id : string, path : string) {
    return new EqualPredicate(id, path);
  }

  public static and(predicates: Predicate<any>[]) {
    return new AndPredicate(predicates);
  }

  public static or(predicates: Predicate<any>[]) {
    return new OrPredicate(predicates);
  }

  public static like(value: string, path : string, type?: LikeType) {
    return new LikePredicate(value, path, type);
  }

  public static in(values: any[], path : string) {
    return new InPredicate(values, path);
  }

  static date(value: DateType, id: string) {
    return new DatePredicate(value, id);
  }
}
