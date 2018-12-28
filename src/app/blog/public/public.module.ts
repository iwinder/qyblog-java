import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PostsContentsComponent } from './posts-contents/posts-contents.component';




const PUBLIC_COMPS = [
  PostsContentsComponent
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [...PUBLIC_COMPS],
  exports: [...PUBLIC_COMPS],
  entryComponents: [],
  providers: []
})
export class BlogArticlePublicModule { }
