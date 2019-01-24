import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared';
import { routedComponents, CommentRoutingModule } from './comment-routing.module';


@NgModule({
    imports: [
        SharedModule,
        CommonModule,
        CommentRoutingModule
    ],
    declarations: [...routedComponents]
})
export class CommentModule { }
