import {WindowCallback} from './meld.window.interfaces';

export class LoadWindow {
  constructor(public loadIndex: number,
              public loadLimit: number,
              public callback?: WindowCallback) {
  }
}

export class ViewPort {
  constructor(public startIndex: number,
              public endIndex: number) {
  }

}
