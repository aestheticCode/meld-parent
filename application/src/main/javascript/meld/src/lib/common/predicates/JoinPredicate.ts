import {Predicate} from './Predicate';

export class JoinPredicate implements Predicate<Predicate<any>> {
  type: string = 'join';
  path : string = null;
  value: Predicate<any>;


  constructor(path: string, value: Predicate<any>) {
    this.path = path;
    this.value = value;
  }
}
