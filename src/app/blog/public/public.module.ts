import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'app/shared';
import { QyBlogArticleFormComponent } from './blog-article-form/blog-article-form.component';


const PUBLIC_COMPS = [
  QyBlogArticleFormComponent
];

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [...PUBLIC_COMPS],
  exports: [...PUBLIC_COMPS],
  entryComponents: [],
  providers: []
})
export class BlogArticlePublicModule { }
