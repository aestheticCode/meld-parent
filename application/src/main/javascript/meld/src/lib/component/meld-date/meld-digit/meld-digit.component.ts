import {Component, EventEmitter, HostListener} from '@angular/core';

@Component({
  selector: 'meld-digit',
  templateUrl: 'meld-digit.component.html',
  styleUrls: ['meld-digit.component.css']
})
export class MeldDigitComponent {

  public _value : number = 0;

  public readonly : boolean = false;

  public limit : number = 30;

  public valueChange : EventEmitter<number> = new EventEmitter();

  private format : Intl.NumberFormat = new Intl.NumberFormat("de-DE", {useGrouping : false, minimumIntegerDigits : 2});

  set digits(value : number) {
    this.format = new Intl.NumberFormat("de-DE", {useGrouping : false, minimumIntegerDigits : value});
  }

  formatValue(value : number) : string {
    return this.format.format(value);
  }

  set value(value : number) {
    if (value <= 1) {
      this._value = 1;
    }
    if (value >= this.limit) {
      this._value = this.limit;
    }
    if (value > 1 && value < this.limit) {
      this._value = value;
    }
  }

  get value() {
    return this._value;
  }

  @HostListener("wheel", ["$event"])
  onWheel(event : WheelEvent) {
    if (! this.readonly) {
      event.stopPropagation();

      let wheelY = this.normalizeWheelY(event);

      this.value -= Math.round(wheelY * 10);

      this.valueChange.emit(this.value);

      return false;
    }
  }

  normalizeWheelY(event : WheelEvent) {
    let wheelDelta = event.wheelDeltaY;
    let detail = event.detail;
    if (detail) {
      if (wheelDelta) {
        return wheelDelta / detail / 40 * detail > 0 ? 1 : -1; // Opera
      }
      else {
        return -detail / 3; // Firefox;         TODO: do not /3 for OS X
      }
    } else {
      return wheelDelta / 120; // IE/Safari/Chrome TODO: /3 for Chrome OS X
    }
  }


}
