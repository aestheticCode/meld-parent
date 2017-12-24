export interface Search {

  index? : number;

  limit? : number;

  filter? : string;

  selected? : any;

}

export interface Callback<V> {
  (data : V[], size: number) : void
}

export interface Selects<V> {
  (search : Search, response : Callback<V>)
}
