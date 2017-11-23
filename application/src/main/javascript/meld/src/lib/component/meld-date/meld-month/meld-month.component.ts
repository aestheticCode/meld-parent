import {Component, EventEmitter, HostListener} from '@angular/core';
import * as moment from "moment";

@Component({
  selector: 'meld-month',
  templateUrl: 'meld-month.component.html',
  styleUrls: ['meld-month.component.css']
})
export class MeldMonthComponent {

  public _value : number = 0;

  public readonly : boolean = false;

  public limit : number = 12;

  public valueChange : EventEmitter<number> = new EventEmitter();

  formatValue(value : number) : string {
    return moment.months(value).slice(0,3);
  }

  set value(value : number) {
    if (value <= 0) {
      this._value = 0;
    }
    if (value >= this.limit - 1) {
      this._value = this.limit - 1;
    }
    if (value > 0 && value < this.limit - 1) {
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

      this.value -= wheelY * 10;

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
