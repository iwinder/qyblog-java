import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './views/user/user-list/user-list.component';
import { RoleListComponent } from './views/role/role-list/role-list.component';
import { UserAddComponent } from './views/user/user-add/user-add.component';
import { UserService } from './service/user.service';
import { UserEditComponent } from './views/user/user-edit/user-edit.component';
import { QyCategoryListComponent } from './views/category/category-list/category-list.component';
import { CategoryService } from './service/category.service';

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
            {
                path: 'category', children: [
                    { path: '', component: QyCategoryListComponent },
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
    providers: [UserService, CategoryService]
})
export class SystemRoutingModule { }

export const routedComponents = [
    UserListComponent,
    RoleListComponent,
    UserAddComponent,
    UserEditComponent,
    QyCategoryListComponent
];
