import {Predicate} from "./Predicate";

export class LikePredicate implements Predicate<string>{

  public type: string = "like";

  public value: string;

  public path : string;

  public search : LikeType = LikeType.STARTS_WITH;


  constructor(value: string, path: string, search?: LikeType) {
    this.value = value;
    this.path = path;
    this.search = search;
  }
}

export enum LikeType {

  CONTAINS,
  STARTS_WITH

}
