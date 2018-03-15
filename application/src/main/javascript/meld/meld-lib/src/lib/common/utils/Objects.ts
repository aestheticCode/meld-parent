export class Objects {

  static isNotNull(value : Object) {
    return ! Objects.isNull(value);
  }

  static isNull(value : Object) {
    return value === null || value === undefined;
  }

}
