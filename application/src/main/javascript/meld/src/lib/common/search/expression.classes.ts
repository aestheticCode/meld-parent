import {
  And,
  Equal,
  ExpressionVisitor,
  In,
  InSelect,
  IsNull,
  Levensthein,
  Like,
  Not,
  Or,
  Path,
  RestExpression,
  SubQuery,
} from './expression.interfaces';
import {LinksContainer} from '../rest/LinksContainer';
import {Link} from '../rest/Link';

export abstract class AbstractExpression implements LinksContainer, RestExpression {
  type: string;

  abstract accept(visitor: ExpressionVisitor);

  links: Link[] = [];
}

export class AndExpression extends AbstractExpression implements And {
  type: string = 'and';

  constructor(public expressions: RestExpression[]) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitAnd(this);
  }
}

export class OrExpression extends AbstractExpression implements Or {
  type: string = 'or';

  constructor(public expressions: RestExpression[]) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitOr(this);
  }
}

export class EqualExpression extends AbstractExpression implements Equal {
  type: string = 'equal';


  constructor(public value: any,
              public name?: string) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitEqual(this);
  }

}

export class InExpression extends AbstractExpression implements In {
  type: string = 'in';

  constructor(public values: any[]) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitIn(this);
  }

}


export class InSelectExpression extends AbstractExpression implements InSelect {
  type: string = 'inSelect';

  constructor(public subQuery: SubQueryExpression) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitInSelect(this);
  }

}

export class IsNullExpression extends AbstractExpression implements IsNull {
  type: string = 'isNull';

  accept(visitor: ExpressionVisitor) {
    visitor.visitNull(this);
  }

}

export class LevenstheinExpression extends AbstractExpression implements Levensthein {
  type: string = 'levensthein';

  constructor(public value: string) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitLevensthein(this);
  }

}

export class LikeExpression extends AbstractExpression implements Like {

  type: string = 'like';

  constructor(public value: string,
              public name?: string) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitLike(this);
  }

}

export class NotExpression extends AbstractExpression implements Not {
  type: string = 'not';

  constructor(public expression: RestExpression) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitNot(this);
  }

}

export class SubQueryExpression extends AbstractExpression implements SubQuery {
  type: string = 'subQuery';

  constructor(public from: string,
              public path: string,
              public expression: RestExpression) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitSubQuery(this);
  }

}

export class PathExpression extends AbstractExpression implements Path {
  type: string = 'path';

  constructor(public path: string,
              public expression: RestExpression) {
    super();
  }

  accept(visitor: ExpressionVisitor) {
    visitor.visitPath(this);
  }

}


export class Expressions {

  public static visit(expression: RestExpression, visitor: ExpressionVisitor) {

    switch (expression.type) {
      case 'and' :
        if (visitor.visitAnd) {
          visitor.visitAnd(<And> expression);

        }
        break;
      case 'or' :
        if (visitor.visitOr) {
          visitor.visitOr(<Or>expression);
        }
        break;
      case 'equal' :
        if (visitor.visitEqual) {
          visitor.visitEqual(<Equal> expression);
        }
        break;
      case 'in' :
        if (visitor.visitIn) {
          visitor.visitIn(<In>expression);
        }
        break;
      case 'inSelect' :
        if (visitor.visitInSelect) {
          visitor.visitInSelect(<InSelect> expression);
        }
        break;
      case 'levensthein' :
        if (visitor.visitLevensthein) {
          visitor.visitLevensthein(<Levensthein>expression);
        }
        break;
      case 'like' :
        if (visitor.visitLike) {
          visitor.visitLike(<Like> expression);
        }
        break;
      case 'not' :
        if (visitor.visitNot) {
          visitor.visitNot(<Not>expression);
        }
        break;
      case 'null' :
        if (visitor.visitNull) {
          visitor.visitNull(<IsNull> expression);
        }
        break;
      case 'path' :
        if (visitor.visitPath) {
          visitor.visitPath(<Path>expression);
        }
        break;
      case 'subQuery' :
        if (visitor.visitSubQuery) {
          visitor.visitSubQuery(<SubQuery> expression);
        }
        break;
    }

  }

}
