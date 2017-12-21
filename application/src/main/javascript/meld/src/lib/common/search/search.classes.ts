import {RestExpression} from './expression.interfaces';
import {
  AndExpression, EqualExpression, InExpression, InSelectExpression, IsNullExpression, LevenstheinExpression, LikeExpression, NotExpression,
  OrExpression, PathExpression, SubQueryExpression
} from './expression.classes';

export class QueryBuilder {

  static query() {
    return new Query();
  }

  static and(expression: RestExpression[]) {
    return new AndExpression(expression);
  }

  static or(expression: RestExpression[]) {
    return new OrExpression(expression);
  }

  static equal(value: any) {
    return new EqualExpression(value);
  }

  static in(values: any[]) {
    return new InExpression(values);
  }

  static inSelect(subQuery: SubQueryExpression) {
    return new InSelectExpression(subQuery);
  }

  public

  static levensthein(value: string, expression: RestExpression) {
    return new LevenstheinExpression(value, expression);
  }

  static like(value: string) {
    return new LikeExpression(value);
  }

  static not(expression: RestExpression) {
    return new NotExpression(expression);
  }

  static subQuery(from: string, path: string, expression: RestExpression) {
    return new SubQueryExpression(from, path, expression);
  }

  static path(path: string, expression: RestExpression) {
    return new PathExpression(path, expression);
  }

  static isNull() {
    return new IsNullExpression();
  }
}

export class Query {

  constructor(public index: number = 0,
              public limit: number = 0,
              public expression?: RestExpression,
              public sorting?: Sort[]) {
  }


}

export class Sort {

  constructor(public path: string,
              public asc: boolean) {
  }

}

