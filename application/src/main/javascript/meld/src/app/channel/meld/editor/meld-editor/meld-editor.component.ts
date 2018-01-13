import {Component, forwardRef, HostListener, Inject, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {MatDialog, MatDialogConfig, MatDialogRef} from "@angular/material";
import {DOCUMENT} from "@angular/common";
import {MeldEditorAvatarDialogComponent} from "./meld-editor-avatar-dialog/meld-editor-avatar-dialog.component";
import {MeldEditorNameDialogComponent} from "./meld-editor-name-dialog/meld-editor-name-dialog.component";
import {MeldEditorComponent as MeldEditor} from "../../../../../lib/component/meld-editor/meld-editor.component";
import {UserRow} from '../../../../usercontrol/user/table/user-table/user-table.interfaces';

const noop = () => {};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MeldEditorComponent),
  multi: true
};

@Component({
  selector: 'app-meld-editor',
  templateUrl: 'meld-editor.component.html',
  styleUrls: ['meld-editor.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
  encapsulation : ViewEncapsulation.None
})
export class MeldEditorComponent implements OnInit, ControlValueAccessor {

  private onTouchedCallback: () => void = noop;
  private onChangeCallback: (value: any) => void = noop;

  public value: string;

  @Input()
  public placeholder: string;

  @ViewChild(MeldEditor)
  private editor: MeldEditor;

  constructor(@Inject(DOCUMENT) private document: HTMLDocument,
              @Inject('Window') private window: Window,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    let nameDialogRef: MatDialogRef<MeldEditorNameDialogComponent>;
    let avatarDialogRef : MatDialogRef<MeldEditorAvatarDialogComponent>;

    const execute = (element : HTMLElement, result : string) => {

      const selection = this.document.getSelection();
      const rangeAt = selection.getRangeAt(0);

      const startContainer = rangeAt.startContainer;

      const startOffset = rangeAt.startOffset;
      const textContent = startContainer.textContent;
      const endOffset = textContent.length;

      const parentElement = startContainer.parentElement;


      if (nameDialogRef) {
         nameDialogRef.componentInstance.doFilter(result);
      } else {
        nameDialogRef = this.dialog.open(MeldEditorNameDialogComponent, this.dialogOptions(this.editor));

        nameDialogRef.afterOpen().subscribe(() => {
          const range = this.document.createRange();
          range.setStart(startContainer, startOffset);
          range.setEnd(startContainer, startOffset);
          range.collapse(true);
          selection.removeAllRanges();
          selection.addRange(range);
        });

        nameDialogRef.afterClosed().subscribe((result: UserRow) => {
          if (result) {
            let html = `<strong class="atom">${result.firstName} ${result.lastName}</strong>`;
            const parser = new DOMParser();
            const resultElement = parser.parseFromString(html, 'application/xml').firstChild;

            resultElement.addEventListener('mousemove', () => {
              if (avatarDialogRef == null) {
                let clientRect = this.editor.getBoundingSelectionRect();
                let top = clientRect.top + 40 + 'px';
                let left = clientRect.left + 'px';
                let config: MatDialogConfig = {
                  disableClose: false,
                  hasBackdrop: false,
                  width: 300 + 'px',
                  position: {top: top, left: left}
                };
                avatarDialogRef = this.dialog.open(MeldEditorAvatarDialogComponent, config);
              }
            });

            resultElement.addEventListener('mouseleave', () => {
              if (avatarDialogRef) {
                avatarDialogRef.close();
                avatarDialogRef = null;
              }
            });

            const textNode = this.document.createTextNode('\u00A0');

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
            range.collapse(true);
            selection.removeAllRanges();
            selection.addRange(range);

            nameDialogRef = null;

          }
        });
      }

    };

    this.editor.commands.push(
      {
        trigger : /.*@([\w\s]*).*/,
        execute : execute
      }
    )
  }

  private dialogOptions(editor: MeldEditor) {
    let clientRect = editor.getBoundingSelectionRect();
    let top = clientRect.top + 40 + "px";
    let left = clientRect.left + "px";
    let config: MatDialogConfig = {
      disableClose: false,
      hasBackdrop: false,
      width: 300 + "px",
      position: {top: top, left: left}
    };
    return config;
  }

  onModelChange(event) {
    this.value = event;
    this.onChangeCallback(event);
  }

  @HostListener('blur')
  onBlur() {
    this.onTouchedCallback();
  }

  writeValue(value: any) {
    if (value !== this.value) {
      this.value = value;
    }
  }

  registerOnChange(fn: (value: any) => any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouchedCallback = fn;
  }

}
