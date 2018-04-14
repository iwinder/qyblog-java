import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContentComponent } from './core/layout/content/content.component';

const routes: Routes = [
    {
        path: '', component: ContentComponent,
        // canActivate: [AuthGuard],
        children: [
            // { path: '', loadChildren: 'app/dashboard/dashboard.module#DashboardModule' },
        ]
    },
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
