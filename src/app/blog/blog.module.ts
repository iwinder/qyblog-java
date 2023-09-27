import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routedComponents, BlogArticleRoutingModule } from './blog-routing.module';
import { SharedModule } from '../shared';
import { BlogArticlePublicModule } from './public/public.module';


@NgModule({
    imports: [
        SharedModule,
        CommonModule,
        BlogArticleRoutingModule,
        BlogArticlePublicModule
    ],
    declarations: [...routedComponents]
})
export class BlogModule { }
