import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContentComponent } from './core/layout/content/content.component';
import { PageNotFoundComponent } from './home/views/page-not-found/page-not-found.component';
import { BlogListIndexComponent } from './blog/views/blog-list-index/blog-list-index.component';
// import { AuthGuard, QyLoginComponent } from './core/AuthGuard';

const newLocal = 'app/system/system.module#SystemModule';
const routes: Routes = [
    {
        path: '', component: ContentComponent,
        // canActivate: [AuthGuard],
        children: [
            {path: '', redirectTo: 'home', pathMatch: 'full'},
            // {path: '', redirectTo: 'dashboard', pathMatch: 'prefix'},
            // { path: 'dashboard', loadChildren: 'app/dashboard/dashboard.module#DashboardModule' },
            // { path: 'system', loadChildren: 'app/system/system.module#SystemModule' },
            { path: 'home',  loadChildren: 'app/home/home.module#HomeModule'  },
            { path: 'article', loadChildren: 'app/blog/blog.module#BlogModule' },
        ],
    },
    {
        path: "notFound",
        component: PageNotFoundComponent
      },
      {
        path: "**",
        redirectTo: "/notFound",
        pathMatch: "full"
      },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
    providers: [
        // CanDeactivateGuard
    ]
})

export class AppRoutingModule { }

export const routedComponents = [
    PageNotFoundComponent,
];
