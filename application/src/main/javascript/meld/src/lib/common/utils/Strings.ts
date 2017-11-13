export class Strings {

  public static isEmpty(value: string) {
    return value || value.length === 0;
  }

  public static isNotEmpty(value: string) {
    return value && value.length > 0;
  }

  static isString(obj: any) {
    return typeof obj === 'string';
  }
}
