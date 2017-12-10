import {Predicate} from './Predicate';

export class SubQueryPredicate implements Predicate<Predicate<any>> {
  type: string = 'subQuery';
  from : string = null;
  path : string = null;
  value: Predicate<any>;


  constructor(from: string, value: Predicate<any>, path: string) {
    this.from = from;
    this.value = value;
    this.path = path;
  }
}
