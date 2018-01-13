import {Directive, ElementRef, HostListener, Input, OnInit, Optional, Self} from '@angular/core';
import {MeldComboBoxComponent} from '../meld-combobox.component';
import {NgControl} from '@angular/forms';

@Directive({
  selector: 'input[meldComboboxInput]'
})
export class MeldComboboxInputDirective implements OnInit {

  @Input('meldComboboxInput')
  private comboBox: MeldComboBoxComponent;

  constructor(private elementRef: ElementRef,
              @Self() @Optional() public ngControl: NgControl) {
  }

  ngOnInit(): void {
    let element: HTMLInputElement = this.elementRef.nativeElement;
    element.addEventListener('input', (value: KeyboardEvent) => {
      this.comboBox.filter = element.value;
      this.comboBox.onShowOverlay();
      this.comboBox.table.refreshItems();
    });
    this.comboBox.filterChange.subscribe((value: string) => {
      this.elementRef.nativeElement.value = value;
    });
  }

  @HostListener('click', ['$event'])
  public onOverlayClick(event: MouseEvent) {
    //event.stopPropagation();
    //return false;
  }

  @HostListener("keyup", ['$event'])
  public onKeyUp(event : KeyboardEvent) {
    this.comboBox.onKeyUp(event)
  }

  @HostListener('focus')
  public onFocus() {
    this.comboBox.onShowOverlay();
  }


}