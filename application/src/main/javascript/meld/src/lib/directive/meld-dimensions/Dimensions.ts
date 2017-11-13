export class Dimensions {
  constructor(public oldOffsetHeight : number,
              public offsetHeight : number,
              public oldOffsetWidth : number,
              public offsetWidth : number) {
  }

  get hasOffsetHeightChanged() : boolean {
    return this.oldOffsetHeight != this.offsetHeight;
  }

  get hasOffsetWidthChanged() : boolean {
    return this.oldOffsetWidth != this.offsetWidth;
  }

}
