import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { SharedModule } from '../../../shared';
import { CategoryRoutingModule, routedComponents } from './category-routing.module';
import { SystemPublicModule } from '../../public/public.module';
// import { SystemRoutingModule, routedComponents } from "./system-routing.module";
// import { SharedModule } from 'app/shared';

@NgModule({
    imports: [
        SharedModule,
        CommonModule,
        CategoryRoutingModule,
        SystemPublicModule
    ],
    declarations: [...routedComponents]
})
export class CategoryModule { }
