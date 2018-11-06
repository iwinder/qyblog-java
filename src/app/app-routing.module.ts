import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContentComponent } from './core/layout/content/content.component';
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
            { path: 'home', loadChildren: 'app/blog/blog.module#BlogModule' },
        ],
    },
    // {path: 'login', component: QyLoginComponent},
    // { path: 'review', loadChildren: 'app/exam/examination/examination.module#ExaminationModule' },
    // { path: '**', component: PageNotFoundComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
    providers: [
        // CanDeactivateGuard
    ]
})
export class AppRoutingModule { }
