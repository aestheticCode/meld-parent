import { Pipe, PipeTransform } from '@angular/core';
import {BinaryFile} from "../../common/rest/BinaryFile";

@Pipe({
  name: 'meldBinaryImage',
  pure : false
})
export class MeldBinaryImagePipe implements PipeTransform {

  private fileNameExtension = /.*\.(.*)/;

  transform(file: BinaryFile, args?: any): any {
    if (file) {
      const regexMatch = this.fileNameExtension.exec(file.name);
      if (regexMatch) {
        return `data:image/${regexMatch[1]};base64,${file.data}`;
      } else {
        return "";
      }
    } else {
      return ""
    }
  }

}
