import {Component, ElementRef, forwardRef, HostListener, Inject, Input, ViewChild} from "@angular/core";
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {DOCUMENT} from "@angular/common";
import {EditorCommand} from "./EditorCommand";
import {EditorAction} from "./EditorAction";
import {EditorCallback} from "./EditorCallback";

const noop = () => {};

function dispatch(command :EditorCommand, action : EditorAction, context?: string, callback?: EditorCallback) {
  command.dispatch(action, context, callback);
  command.action = action;
}

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldEditorComponent),
  multi: true
};

@Component({
  selector: 'meld-editor',
  templateUrl: 'meld-editor.component.html',
  styleUrls: ['meld-editor.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class MeldEditorComponent implements ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  @Input()
  public placeholder: string;

  @ViewChild("editor")
  private editor: ElementRef;

  public commands: EditorCommand[] = [];

  constructor(@Inject(DOCUMENT) private document: HTMLDocument,
              @Inject('Window') private window: Window) {
  }

  onPaste(event) {
    event.preventDefault();
    let text = event.clipboardData.getData("text/plain");
    document.execCommand("insertHTML", false, text);
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

  onKeyUp(event: KeyboardEvent) {
    const selection = this.document.getSelection();
    const rangeAt = selection.getRangeAt(0);

    const startContainer = rangeAt.startContainer;

    const startOffset = rangeAt.startOffset;
    const textContent = startContainer.textContent;
    const endOffset = textContent.length;

    const parentElement = startContainer.parentElement;
    const text = textContent.substr(0, startOffset);

    this.commands.forEach((command) => {
      if (command.trigger.test(text)) {

        let regexResult = command.trigger.exec(text);

        if (command.action === EditorAction.DEACTIVATE) {
          dispatch(command, EditorAction.ACTIVATE, regexResult[1], (resultElement) => {

            const textNode = this.document.createTextNode("\u00A0");

            const prefix = textContent.substr(0, startOffset - 1);
            const suffix = textContent.substr(startOffset, endOffset);
            const prefixText = this.document.createTextNode(prefix);
            const suffixText = this.document.createTextNode(suffix);

            parentElement.insertBefore(prefixText, startContainer);
            parentElement.insertBefore(resultElement, startContainer);
            parentElement.insertBefore(textNode, startContainer);
            parentElement.insertBefore(suffixText, startContainer);
            parentElement.removeChild(startContainer);

            const range = this.document.createRange();
            range.setStart(textNode, 1);
            range.setEnd(textNode, 1);
            range.collapse(true)
            const selection = this.window.getSelection();
            selection.removeAllRanges();
            selection.addRange(range);


            this.onChangeCallback(this.editor.nativeElement.innerHTML);

          });
        } else {
          dispatch(command, EditorAction.UPDATE, regexResult[1])
        }

      } else {

        dispatch(command, EditorAction.DEACTIVATE);

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
