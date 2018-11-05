import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BlogArticleService } from './service/blog-article.service';
import { BlogListIndexComponent } from './views/blog-list-index/blog-list-index.component';


const routes: Routes = [
    {
        path: '', children: [
            {
                path: 'article', children: [
                    { path: '', redirectTo: 'list', pathMatch: 'full' },
                    { path: 'list', component: BlogListIndexComponent },
                    // { path: 'add', component: BlogArticleAddComponent },
                    // { path: ':articleId/edit', component: BlogArticleEditComponent },

                ]
            },
            {
                path: 'category', loadChildren: 'app/system/views/category/category.module#CategoryModule',
            }
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
    BlogListIndexComponent,
];
