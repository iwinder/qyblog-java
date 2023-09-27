import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeListIndexComponent } from './views/home-list-index/home-list-index.component';
import { BlogArticleService } from '../blog/service/blog-article.service';



const routes: Routes = [
    { path: '', component: HomeListIndexComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [BlogArticleService]
})
export class  HomeRoutingModule { }

export const routedComponents = [
    HomeListIndexComponent
];
