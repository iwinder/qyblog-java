import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './views/user/user-list/user-list.component';
import { RoleListComponent } from './views/role/role-list/role-list.component';
import { UserAddComponent } from './views/user/user-add/user-add.component';
import { UserService } from './service/user.service';
import { UserEditComponent } from './views/user/user-edit/user-edit.component';

const routes: Routes = [
    {
        path: '', children: [
            {
                path: 'user', children: [
                    { path: '', redirectTo: 'list', pathMatch: 'full' },
                    { path: 'list', component: UserListComponent },
                    { path: 'add', component: UserAddComponent },
                    { path: ':userId/edit', component: UserEditComponent }

                ]
            },
            {
                path: 'role', children: [
                    { path: '', component: RoleListComponent },
                    // { path: 'add', component: UgcExampleCourseEditComponent },
                    // { path: ':id/edit', component: UgcExampleCourseEditComponent },

                ],
            },
            // {
            //     path: 'category', loadChildren: 'app/system/views/category/category.module#CategoryModule',
            // }
            // { path: 'banner', component: UgcBannerListComponent },
            // { path: 'notice-box', component: UgcNoticeBoxListComponent }
        ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [UserService]
})
export class SystemRoutingModule { }

export const routedComponents = [
    UserListComponent,
    RoleListComponent,
    UserAddComponent,
    UserEditComponent
];
