export interface Search {

  index? : number;

  limit? : number;

  filter? : string;

  selected? : any;

}

export interface SelectCallback<V> {
  (data : V[], size: number) : void
}

export interface Selects<V> {
  (search : Search, response : SelectCallback<V>)
}
