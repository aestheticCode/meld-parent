import {Predicate} from "./Predicate";

export class DatePredicate implements Predicate<DateType>{

  public type: string = "date";

  public value: DateType;

  public path : string;

  constructor(value: DateType, path: string) {
    this.value = value;
    this.path = path;
  }
}

export interface DateType {

  lt : Date

  gt : Date

}

