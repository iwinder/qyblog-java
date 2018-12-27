import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routedComponents, BlogArticleRoutingModule } from './blog-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



// // alternatively if you only need to include a subset of languages
// const hljs: any = require('highlight.js/lib/highlight');
// hljs.registerLanguage('typescript', require('highlight.js/lib/languages/typescript'));


@NgModule({
    imports: [
        CommonModule,
        BlogArticleRoutingModule,
        NgbModule,
    ],
    declarations: [...routedComponents]
})
export class BlogModule { }
