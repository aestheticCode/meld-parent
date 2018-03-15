import {ElementRef} from "@angular/core";
import {ControlValueAccessor} from "@angular/forms";

const noop = () => {};

export abstract class AbstractFileInput implements ControlValueAccessor {

  protected onTouchedCallback: () => void = noop;
  protected onChangeCallback: (value: any) => void = noop;

  constructor(protected elementRef : ElementRef<HTMLInputElement>) {
    const element = elementRef.nativeElement;

    element.addEventListener("change", (event : Event)=> {
      const reader = new FileReader();
      const inputFile : HTMLInputElement = event.target as HTMLInputElement;
      const files : FileList = inputFile.files;

      const promises = Array.from(files).map((file) => {
        return new Promise((resolve, reject) => {
          reader.readAsBinaryString(file.slice());
          reader.addEventListener("load", (result) => {
            this.value = {
              name : file.name,
              lastModified : file.lastModifiedDate,
              data : btoa(reader.result)
            };
            resolve();
          });

          reader.addEventListener("error", () => {
            reject();
          })
        });
      });

      Promise.all(promises)
        .then(() => {
          this.onChangeCallback(this.value);
        })
        .catch(() => {
          throw new Error("When selecting something went wrong");
        })
    });
  }

  abstract get value(): any

  abstract set value(value: any)

  writeValue(obj: any): void {
    if (obj) {
      this.value = obj;
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }


}
