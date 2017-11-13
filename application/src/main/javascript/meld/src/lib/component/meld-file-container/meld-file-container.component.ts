import { Component, ElementRef} from '@angular/core';

@Component({
  selector: 'meld-file-container',
  templateUrl: 'meld-file-container.component.html',
  styleUrls: ['meld-file-container.component.css']
})
export class MeldFileContainerComponent {

  constructor(private elementRef : ElementRef) { }

  public click() {
    const element : HTMLElement = this.elementRef.nativeElement;
    const inputElement : HTMLInputElement = element.querySelector("input[type=file]") as HTMLInputElement;

    inputElement.click();
  }

}
