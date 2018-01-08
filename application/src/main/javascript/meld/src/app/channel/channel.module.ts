import {NgModule} from '@angular/core';
import {LibModule} from '../../lib/lib.module';
import {appRoutingProviders, routing} from './channel.routing';
import {SocialModule} from '../social/social.module';
import {MediaModule} from '../media/media.module';
import {MeldFormComponent} from './meld/form/meld-form/meld-form.component';
import {MeldListComponent} from './meld/list/meld-list/meld-list.component';
import {MeldEditorComponent} from './meld/editor/meld-editor/meld-editor.component';
import {MeldCommentComponent} from './meld/comment/meld-comment/meld-comment.component';
import {MeldItemComponent} from './meld/list/meld-list/meld-item/meld-item.component';
import {MeldCommentDialogComponent} from './meld/comment/meld-comment/meld-comment-dialog/meld-comment-dialog.component';
import {MeldEditorNameDialogComponent} from './meld/editor/meld-editor/meld-editor-name-dialog/meld-editor-name-dialog.component';
import {MeldEditorAvatarDialogComponent} from './meld/editor/meld-editor/meld-editor-avatar-dialog/meld-editor-avatar-dialog.component';
import {MeldImageFormComponent} from './meld/form/meld-form/meld-image-form/meld-image-form.component';
import {MeldTextFormComponent} from './meld/form/meld-form/meld-text-form/meld-text-form.component';
import {MeldYoutubeFormComponent} from './meld/form/meld-form/meld-youtube-form/meld-youtube-form.component';
import {MeldTextItemComponent} from './meld/list/meld-list/meld-item/meld-text-item/meld-text-item.component';
import {MeldYoutubeItemComponent} from './meld/list/meld-list/meld-item/meld-youtube-item/meld-youtube-item.component';
import {MeldImageItemComponent} from './meld/list/meld-list/meld-item/meld-image-item/meld-image-item.component';
import {MeldPhotoFormComponent} from './meld/form/meld-form/meld-photo-form/meld-photo-form.component';
import {MeldPhotoItemComponent} from './meld/list/meld-list/meld-item/meld-photo-item/meld-photo-item.component';
import { MeldLinkFormComponent } from './meld/form/meld-form/meld-link-form/meld-link-form.component';
import { MeldLinkItemComponent } from './meld/list/meld-list/meld-item/meld-link-item/meld-link-item.component';


@NgModule({
  imports: [
    LibModule,
    SocialModule,
    MediaModule,
    routing
  ],
  declarations: [
    MeldFormComponent,
    MeldListComponent,
    MeldEditorComponent,
    MeldCommentComponent,
    MeldItemComponent,
    MeldCommentDialogComponent,
    MeldEditorNameDialogComponent,
    MeldEditorAvatarDialogComponent,
    MeldImageFormComponent,
    MeldTextFormComponent,
    MeldYoutubeFormComponent,
    MeldTextItemComponent,
    MeldYoutubeItemComponent,
    MeldImageItemComponent,
    MeldPhotoFormComponent,
    MeldPhotoItemComponent,
    MeldLinkFormComponent,
    MeldLinkItemComponent
  ],
  providers: [
    appRoutingProviders
  ],
  entryComponents: [
    MeldEditorNameDialogComponent,
    MeldEditorAvatarDialogComponent,
    MeldCommentDialogComponent
  ]
})
export class ChannelModule {
}
