import {Predicate} from './Predicate';

export class IsNullPredicate implements Predicate<any> {
  type: string = "isNull";
  value: any;

  constructor(public path : string) {}
}
