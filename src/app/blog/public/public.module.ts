import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PostsContentsComponent } from './posts-contents/posts-contents.component';
import { MarkdownModule } from 'ngx-markdown';




const PUBLIC_COMPS = [
  PostsContentsComponent
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MarkdownModule.forChild(),
  ],
  declarations: [...PUBLIC_COMPS],
  exports: [...PUBLIC_COMPS],
  entryComponents: [],
  providers: []
})
export class BlogArticlePublicModule { }
