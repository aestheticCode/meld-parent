import {RestExpression} from './expression.interfaces';

export class AndExpression implements RestExpression {
  type: string = 'and';

  constructor(public expressions: RestExpression[]) {
  }
}

export class OrExpression implements RestExpression {
  type: string = 'or';

  constructor(public expressions: RestExpression[]) {
  }
}

export class EqualExpression implements RestExpression {
  type: string = 'equal';

  constructor(public value: any) {
  }
}

export class InExpression implements RestExpression {
  type: string = 'in';

  constructor(public values: any[]) {
  }
}

export class InSelectExpression implements RestExpression {
  type: string = 'inSelect';

  constructor(public subQuery: SubQueryExpression) {
  }
}

export class IsNullExpression implements RestExpression {
  type: string = 'isNull';
}

export class LevenstheinExpression implements RestExpression {
  type: string = 'levensthein';

  constructor(public value: string,
              public expression: RestExpression) {
  }
}

export class LikeExpression implements RestExpression {
  type: string = 'like';

  constructor(public value: string) {
  }
}

export class NotExpression implements RestExpression {
  type: string = 'not';

  constructor(public expression: RestExpression) {
  }
}

export class SubQueryExpression implements RestExpression {
  type: string = 'subQuery';

  constructor(public from: string,
              public path: string,
              public expression: RestExpression) {
  }
}

export class PathExpression implements RestExpression {
  type: string = 'path';

  constructor(public path: string,
              public expression: RestExpression) {
  }
}
