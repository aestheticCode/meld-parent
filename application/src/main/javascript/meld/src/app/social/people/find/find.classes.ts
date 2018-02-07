import {Filter, RestExpression, SortExpression} from './find.interfaces';
import {Link} from '../../../../lib/common/rest/Link';

export class AbstractExpressionModel implements RestExpression {
  links: Link[];
  type: "and" | "name" | "noop" | "or" | "school";
}

export class AndExpressionModel extends AbstractExpressionModel {
  type: "and" = "and";
  value: RestExpression[];

  constructor(value: RestExpression[]) {
    super();
    this.value = value;
  }
}

export class NameExpressionModel extends AbstractExpressionModel {
  type: "name" = "name";
  value: string;

  constructor(value: string) {
    super();
    this.value = value;
  }
}

export class NoopExpressionModel extends AbstractExpressionModel {
  type: "noop" = "noop";
}

export class OrExpressionModel extends AbstractExpressionModel {
  type: "or" = "or";
  value: RestExpression[];

  constructor(value: RestExpression[]) {
    super();
    this.value = value;
  }
}

export class SchoolExpressionModel extends AbstractExpressionModel {
  type: "school" = "school";
  value: string;

  constructor(value: string) {
    super();
    this.value = value;
  }
}

export class LevenstheinExpressionModel implements SortExpression {
  type: "levensthein" = "levensthein";
  paths: string[];
  asc: boolean;
  value: string;

  constructor(paths: string[], asc: boolean, value: string) {
    this.paths = paths;
    this.asc = asc;
    this.value = value;
  }
}

export class NormalExpressionModel implements SortExpression {
  type: "normal" = "normal";
  path: string;
  asc: boolean;


  constructor(path: string, asc: boolean) {
    this.path = path;
    this.asc = asc;
  }
}

export class FilterModel implements Filter {
  name: string;
  active : boolean;
  expression: RestExpression;

  constructor(name: string, expression: RestExpression) {
    this.name = name;
    this.expression = expression;
  }
}

export class SearchModel {
  index: number;
  limit: number;
  expression: RestExpression;
  sorting: SortExpression[];

  constructor(index: number, limit: number, expression: RestExpression, sorting: SortExpression[]) {
    this.index = index;
    this.limit = limit;
    this.expression = expression;
    this.sorting = sorting;
  }
}
