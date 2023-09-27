import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routedComponents, BlogArticleRoutingModule } from './blog-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BlogArticlePublicModule } from './public/public.module';
import { CommonPublicModule } from 'app/common/public';



// // alternatively if you only need to include a subset of languages
// const hljs: any = require('highlight.js/lib/highlight');
// hljs.registerLanguage('typescript', require('highlight.js/lib/languages/typescript'));


@NgModule({
    imports: [
        CommonModule,
        BlogArticleRoutingModule,
        NgbModule,
        BlogArticlePublicModule,
        CommonPublicModule
    ],
    declarations: [...routedComponents]
})
export class BlogModule { }
