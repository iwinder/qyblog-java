import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routedComponents, BlogArticleRoutingModule } from './blog-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
    imports: [
        CommonModule,
        BlogArticleRoutingModule,
        NgbModule
    ],
    declarations: [...routedComponents]
})
export class BlogModule { }
