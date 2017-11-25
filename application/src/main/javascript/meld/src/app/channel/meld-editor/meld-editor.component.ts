import {Component, forwardRef, HostListener, Inject, Input, OnInit, ViewChild} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {MatDialog, MatDialogConfig, MatDialogRef} from "@angular/material";
import {DOCUMENT} from "@angular/common";
import {MeldEditorAvatarDialogComponent} from "./meld-editor-avatar-dialog/meld-editor-avatar-dialog.component";
import {MeldEditorNameDialogComponent} from "./meld-editor-name-dialog/meld-editor-name-dialog.component";
import {MeldEditorComponent as MeldEditor} from "../../../lib/component/meld-editor/meld-editor.component";
import {EditorCommand} from "../../../lib/component/meld-editor/EditorCommand";
import {EditorAction} from "../../../lib/component/meld-editor/EditorAction";
import {EditorCallback} from "../../../lib/component/meld-editor/EditorCallback";
import {UserRow} from "../../usercontrol/user-table/user-table.interfaces";

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
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
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
    const editor = this.editor;
    const dialog = this.dialog;
    let nameDialogRef: MatDialogRef<MeldEditorNameDialogComponent>;
    let avatarDialogRef : MatDialogRef<MeldEditorAvatarDialogComponent>;

    this.editor.commands.push(
      new class extends EditorCommand {

        constructor() {
          super(/.*@([\w\s]*).*/);
        }

        dispatch(action: EditorAction, context?: string, callback?: EditorCallback) {
          switch (action) {
            case EditorAction.ACTIVATE : {
              let clientRect = editor.getBoundingSelectionRect();
              let top = clientRect.top + 40 + "px";
              let left = clientRect.left + "px";
              let config: MatDialogConfig = {
                disableClose: false,
                hasBackdrop: false,
                width: 300 + "px",
                position: {top: top, left: left}
              };

              nameDialogRef = dialog.open(MeldEditorNameDialogComponent, config);

              nameDialogRef.afterClosed().subscribe((result: UserRow) => {
                if (result) {
                  let html = `<strong>${result.firstName} ${result.lastName}</strong>`;
                  const parser = new DOMParser();
                  const resultElement = parser.parseFromString(html, "application/xml").firstChild;

                  resultElement.addEventListener("mousemove", () => {
                    if (avatarDialogRef == null) {
                      let clientRect = editor.getBoundingSelectionRect();
                      let top = clientRect.top + 40 + "px";
                      let left = clientRect.left + "px";
                      let config: MatDialogConfig = {
                        disableClose: false,
                        hasBackdrop: false,
                        width: 300 + "px",
                        position: {top: top, left: left}
                      };
                      avatarDialogRef = dialog.open(MeldEditorAvatarDialogComponent, config)
                    }
                  });

                  resultElement.addEventListener("mouseleave", () => {
                    if (avatarDialogRef) {
                      avatarDialogRef.close();
                      avatarDialogRef = null;
                    }
                  });

                  this.action = EditorAction.DEACTIVATE;
                  callback(resultElement);
                }
              });
            } break;
            case EditorAction.UPDATE : {
              nameDialogRef.componentInstance.doFilter(context);
            } break;
            case EditorAction.DEACTIVATE : {
              if (nameDialogRef) {
                nameDialogRef.close();
              }
            }
          }
        }

      }
    )
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
