import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BlogArticleListComponent } from './views/blog-article/blog-article-list/blog-article-list.component';
import { BlogArticleAddComponent } from './views/blog-article/blog-article-add/blog-article-add.component';
import { BlogArticleService } from './service/blog-article.service';

const routes: Routes = [
    {
        path: '', children: [
            {
                path: 'article', children: [
                    { path: '', redirectTo: 'list', pathMatch: 'full' },
                    { path: 'list', component: BlogArticleListComponent },
                    { path: 'add', component: BlogArticleAddComponent },
                    // { path: ':id/edit', component: UgcExampleCourseEditComponent },

                ]
            },
        ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [BlogArticleService]
})
export class  BlogArticleRoutingModule { }

export const routedComponents = [
    BlogArticleListComponent,
    BlogArticleAddComponent
];
