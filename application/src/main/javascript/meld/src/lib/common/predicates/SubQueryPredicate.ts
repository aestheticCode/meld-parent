import {Predicate} from './Predicate';

export class SubQueryPredicate implements Predicate<Predicate<any>> {
  type: string = 'subQuery';
  from : string = null;
  select : string = null;
  path : string = null;
  value: Predicate<any>;


  constructor(select : string, from: string, value: Predicate<any>, path: string) {
    this.select = select;
    this.from = from;
    this.value = value;
    this.path = path;
  }
}
