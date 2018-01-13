import {Component, ElementRef, forwardRef, HostListener, Inject, Input, ViewChild, ViewEncapsulation} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {DOCUMENT} from '@angular/common';
import {EditorCommand} from './meld-editor.interfaces';

const noop = () => {
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldEditorComponent),
  multi: true
};

@Component({
  selector: 'meld-editor',
  templateUrl: 'meld-editor.component.html',
  styleUrls: ['meld-editor.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
  encapsulation : ViewEncapsulation.None
})
export class MeldEditorComponent implements ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  @Input()
  public placeholder: string;

  @ViewChild('editor')
  private editor: ElementRef;

  public commands: EditorCommand[] = [];

  constructor(@Inject(DOCUMENT) private document: HTMLDocument,
              @Inject('Window') private window: Window) {
  }

  onPaste(event) {
    event.preventDefault();
    let text = event.clipboardData.getData('text/plain');
    document.execCommand('insertHTML', false, text);
    this.onChangeCallback(this.editor.nativeElement.innerHTML);
    return false;
  }

  @HostListener('blur')
  onBlur() {
    this.onTouchedCallback();
  }

  public getBoundingSelectionRect(): ClientRect {
    const selection = this.document.getSelection();
    const rangeAt = selection.getRangeAt(0);

    let startContainer = rangeAt.startContainer;
    let element;
    if (startContainer instanceof HTMLElement) {
      element = startContainer;
    } else {
      element = startContainer.parentElement;
    }

    return element.getBoundingClientRect();
  }

  onKeyDown(event: KeyboardEvent) {
    const selection = this.document.getSelection();
    const rangeAt = selection.getRangeAt(0);

    const startContainer = rangeAt.startContainer;
    const parentElement = startContainer.parentElement;


    if (parentElement.className == "atom") {
      if (event.key.length === 1) {
        event.stopPropagation();
        return false;
      }

      if (event.keyCode === 8) {
        event.stopPropagation();

        if (rangeAt.startOffset === startContainer.textContent.length) {
          const textNode = this.document.createTextNode('\u00A0');
          parentElement.parentElement.appendChild(textNode);

          if (event.keyCode === 8) {
            parentElement.remove();
          }
        }
        return false;

      }

    }

    return true;
  }

  onKeyUp(event: KeyboardEvent) {
    const selection = this.document.getSelection();
    const rangeAt = selection.getRangeAt(0);

    const startContainer = rangeAt.startContainer as HTMLElement;

    const startOffset = rangeAt.startOffset;
    const textContent = startContainer.textContent;

    const text = textContent.substr(0, startOffset);

    this.commands.forEach((command) => {
      if (command.trigger.test(text)) {

        let regexResult = command.trigger.exec(text);

        command.execute(startContainer, regexResult[1]);

      }
    });


    this.onChangeCallback(this.editor.nativeElement.innerHTML);

  }

  writeValue(value: any) {
    let nativeElement: HTMLDivElement = this.editor.nativeElement;
    if (value) {
      nativeElement.innerHTML = value;
    }
  }

  registerOnChange(fn: (value: any) => any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouchedCallback = fn;
  }

}
