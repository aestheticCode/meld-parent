import {Directive, EventEmitter, Input, Output} from '@angular/core';
import {NgControl} from '@angular/forms';

@Directive({
  selector: '[meldDebounce]'
})
export class MeldDebounceDirective {

  @Output("meldModelChange")
  public onDebounce = new EventEmitter<any>();

  @Input('meldDebounce')
  public debounceTime: number = 500;

  private isFirstChange: boolean = true;

  constructor(private model: NgControl) { }

  ngOnInit() {
    this.model.valueChanges
      .debounceTime(this.debounceTime)
      .distinctUntilChanged()
      .subscribe(modelValue => {
        if (this.isFirstChange) {
          this.isFirstChange = false;
        } else {
          this.onDebounce.emit(modelValue);
        }
      });
  }

}
