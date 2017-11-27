import {Component, ElementRef, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'meld-tab',
  templateUrl: 'meld-tab.component.html',
  styleUrls: ['meld-tab.component.css']
})
export class MeldTabComponent {

  @Input("label")
  label : string;

  @Input("active")
  active : boolean;

  @Output("activeChange")
  activeChange : EventEmitter<boolean> = new EventEmitter();


  constructor(private elementRef : ElementRef) {
  }

  set width(value : number) {
    this.elementRef.nativeElement.style.width = value + 'px';
  }

  disable() {
    this.active = false;
  }

  enable() {
    this.active = true;
    this.activeChange.emit(true);
  }
}
