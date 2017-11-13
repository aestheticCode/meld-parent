import {WindowCallback} from "./WindowCallback";

export class LoadWindow {
  constructor(public loadIndex : number,
              public loadLimit : number,
              public startIndex : number,
              public endIndex : number,
              public callback? : WindowCallback) {
  }
}
