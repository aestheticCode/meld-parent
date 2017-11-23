import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common'
import {HttpModule} from '@angular/http';
import {FormsModule} from '@angular/forms';
import {LibModule} from "../../lib/lib.module";
import {appRoutingProviders, routing} from "./channel.routing";
import {MeldFormComponent} from './meld-form/meld-form.component';
import {MeldListComponent} from './meld-list/meld-list.component';
import {MeldEditorComponent} from "./meld-editor/meld-editor.component";
import {MeldCommentComponent} from "./meld-comment/meld-comment.component";
import {MeldItemComponent} from "./meld-list/meld-item/meld-item.component";
import {MeldEditorNameDialogComponent} from "./meld-editor/meld-editor-name-dialog/meld-editor-name-dialog.component";
import {MeldEditorAvatarDialogComponent} from "./meld-editor/meld-editor-avatar-dialog/meld-editor-avatar-dialog.component";


@NgModule({
  imports: [
    LibModule,
    routing
  ],
  declarations: [
    MeldFormComponent,
    MeldListComponent,
    MeldEditorComponent,
    MeldCommentComponent,
    MeldItemComponent,
    MeldEditorNameDialogComponent,
    MeldEditorAvatarDialogComponent
  ],
  providers: [
    appRoutingProviders
  ],
  entryComponents : [
    MeldEditorNameDialogComponent,
    MeldEditorAvatarDialogComponent
  ]
})
export class ChannelModule {
}
