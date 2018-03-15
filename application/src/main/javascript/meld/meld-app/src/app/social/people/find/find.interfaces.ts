import {BinaryFile} from '@aestheticcode/meld-lib';
import {LinksContainer} from '@aestheticcode/meld-lib';

export interface UserRow {

  id: string

  image: BinaryFile

  name: string

  firstName: string

  lastName: string

  category: string

}


export interface AbstractExpression extends RestExpression {
  type: "and" | "name" | "noop" | "or" | "school";
}

export interface AndExpression extends AbstractExpression {
  type: "and";
  value: RestExpression[];
}

export interface NameExpression extends AbstractExpression {
  type: "name";
  value: string;
}

export interface NoopExpression extends AbstractExpression {
  type: "noop";
}

export interface OrExpression extends AbstractExpression {
  type: "or";
  value: RestExpression[];
}

export interface RestExpression extends LinksContainer {
  type: "and" | "name" | "noop" | "or" | "school";
}

export interface SchoolExpression extends AbstractExpression {
  type: "school";
  value: string;
}

export interface LevenstheinExpression extends SortExpression {
  type: "levensthein";
  paths: string[];
  asc: boolean;
  value: string;
}

export interface NormalExpression extends SortExpression {
  type: "normal";
  path: string;
  asc: boolean;
}

export interface SortExpression {
  type: "levensthein" | "normal";
}

export interface Filter {
  name: string;
  active : boolean;
  expression: RestExpression;
}

export interface Search {
  index: number;
  limit: number;
  expression: RestExpression;
  sorting: SortExpression[];
}

export interface SortVisitor {
}

