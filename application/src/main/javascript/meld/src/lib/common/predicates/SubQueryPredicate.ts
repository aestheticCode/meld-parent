import {Predicate} from './Predicate';

export class SubQueryPredicate implements Predicate<Predicate<any>> {
  type: string = 'subQuery';

  constructor(public from: string,
              public path: string,
              public value: Predicate<any>) {
  }
}

