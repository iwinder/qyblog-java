import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SysCommentSettingComponent } from './views/sys-comment-setting/sys-comment-setting.component';
import { CommentListComponent } from './views/comment-list/comment-list.component';
import { CommentService } from './service/comment.service';
import { SysCommentSettingService } from './service/sys-comment-setting.service';

const routes: Routes = [
    {
        path: '', children: [
            {
                path: 'list', component: CommentListComponent,
            },
            {
                path: 'sysComment', component: SysCommentSettingComponent,
            }
        ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [CommentService, SysCommentSettingService]
})
export class CommentRoutingModule { }

export const routedComponents = [
    CommentListComponent,
    SysCommentSettingComponent
];
