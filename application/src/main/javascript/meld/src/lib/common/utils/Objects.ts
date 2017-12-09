export class Objects {

  static isNotNull(value : Object) {
    return value !== null || value !== undefined;
  }

  static isNull(value : Object) {
    return value === null || value === undefined;
  }

}
