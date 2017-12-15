import {Predicate} from './Predicate';

export class NotPredicate implements Predicate<Predicate<any>> {
  type: string = 'not';

  constructor(public value: Predicate<any>) {}
}
