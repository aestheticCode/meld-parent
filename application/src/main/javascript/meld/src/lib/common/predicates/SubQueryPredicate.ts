import {Predicate} from './Predicate';

export class SubQueryPredicate implements Predicate<Predicate<any>> {
  type: string = 'subQuery';

  constructor(public selectPath: string,
              public select: string,
              public from: string,
              public fromPath: string,
              public value: Predicate<any>) {
  }
}

