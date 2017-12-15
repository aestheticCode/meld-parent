import {Predicate} from "../predicates/Predicate";
import {Query} from "./Query";
import {AndPredicate} from "../predicates/AndPredicate";
import {OrPredicate} from "../predicates/OrPedicate";
import {LikePredicate, LikeType} from "../predicates/LikePredicate";
import {InPredicate} from "../predicates/InPredicate";
import {EqualPredicate} from "../predicates/EqualPredicate";
import {DatePredicate, DateType} from "../predicates/DatePredicate";
import {JoinPredicate} from '../predicates/JoinPredicate';
import {SubQueryPredicate} from '../predicates/SubQueryPredicate';
import {InSelectPredicate} from '../predicates/InSelectPredicate';
import {IsNullPredicate} from '../predicates/IsNullPredicate';
import {NotPredicate} from '../predicates/NotPredicate';

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

  public static inSelect(path: string, values: SubQueryPredicate) {
    return new InSelectPredicate(values, path);
  }

  static date(value: DateType, id: string) {
    return new DatePredicate(value, id);
  }

  static join(value : Predicate<any>, path : string) {
    return new JoinPredicate(path, value)
  }

  static subQuery(from : string, path : string, value : Predicate<any>) {
    return new SubQueryPredicate(from, path, value);
  }

  static isNull(path : string) {
    return new IsNullPredicate(path);
  }

  static not(predicate : Predicate<any>) {
    return new NotPredicate(predicate);
  }
}
