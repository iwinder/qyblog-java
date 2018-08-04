import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './views/user/user-list/user-list.component';
import { RoleListComponent } from './views/role/role-list/role-list.component';

const routes: Routes = [
    {
        path: '', children: [
            {
                path: 'user', children: [
                    { path: '', component: UserListComponent },
                    // { path: 'add', component: UgcExampleCourseEditComponent },
                    // { path: ':id/edit', component: UgcExampleCourseEditComponent },

                ]
            },
            {
                path: 'role', children: [
                    // { path: '', component: RoleListComponent },
                    // { path: 'add', component: UgcExampleCourseEditComponent },
                    // { path: ':id/edit', component: UgcExampleCourseEditComponent },

                ],
            }
            // { path: 'banner', component: UgcBannerListComponent },
            // { path: 'notice-box', component: UgcNoticeBoxListComponent }
        ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: []
})
export class SystemRoutingModule { }

export const routedComponents = [
    UserListComponent,
    RoleListComponent
];
