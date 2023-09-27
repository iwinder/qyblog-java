import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BlogArticleService } from './service/blog-article.service';
import { BlogListIndexComponent } from './views/blog-list-index/blog-list-index.component';
import { BlogInfoComponent } from './views/blog-info/blog-info.component';


const routes: Routes = [
    { path: '', component: BlogListIndexComponent },
    { path: ':articleId', component: BlogInfoComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [BlogArticleService]
})
export class  BlogArticleRoutingModule { }

export const routedComponents = [
    BlogListIndexComponent,
    BlogInfoComponent
];
