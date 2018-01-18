import {LinksContainer} from '../rest/LinksContainer';

export interface RestExpression extends LinksContainer {
  type : string;
  accept(visitor : ExpressionVisitor)
}

export interface Levensthein extends RestExpression {
  value: string;
}

export interface Like extends RestExpression {
  value: string;
  name? : string
}


export interface Not extends RestExpression {
  expression: RestExpression;
}

export interface SubQuery extends RestExpression {
  from: string;
  path: string;
  expression: RestExpression;
}


export interface Path extends RestExpression {
  path: string;
  expression: RestExpression;
}

export interface And extends RestExpression {
  expressions: RestExpression[];
}


export interface Or extends RestExpression {
  expressions: RestExpression[];
}

export interface Equal extends RestExpression {
  name? : string;
  value: any;
}

export interface In extends RestExpression {
  values: any[];
}

export interface InSelect extends RestExpression {
  subQuery: SubQuery;
}

export interface IsNull extends RestExpression {}


export interface ExpressionVisitor {

  visitAnd(expression: And): void;

  visitOr(expression: Or): void;

  visitEqual(expression: Equal): void;

  visitIn(expression: In): void;

  visitInSelect(expression: InSelect): void;

  visitNull(expression: IsNull): void;

  visitLevensthein(expression: Levensthein): void;

  visitLike(expression: Like): void;

  visitEqual(expression: Equal): void;

  visitNot(expression: Not): void;

  visitSubQuery(expression: SubQuery): void;

  visitPath(expression: Path): void;

}
