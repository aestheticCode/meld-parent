import {Phone} from "./phone-form.interfaces";

export class PhoneModel implements Phone {
  number: string;
  type: string;
}

export class TypeModel {

  constructor(private code: string,
              private name: string) {

  }

}
