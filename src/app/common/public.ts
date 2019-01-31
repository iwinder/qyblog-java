import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommetsService } from './service/comment-list.service';
import { CommentListComponent } from './views/comment/comment-list/comment-list.component';
import { CommentChildListComponent } from './views/comment/comment-child-list/comment-child-list.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReplyFormComponent } from './views/reply-form/reply-form.component';
// import { CookieService } from 'ngx-cookie-service';




const PUBLIC_COMPS = [
    CommentListComponent,
    CommentChildListComponent,
    ReplyFormComponent
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  declarations: [...PUBLIC_COMPS],
  exports: [...PUBLIC_COMPS],
  entryComponents: [],
  providers: [
    CommetsService]
})
export class CommonPublicModule { }
