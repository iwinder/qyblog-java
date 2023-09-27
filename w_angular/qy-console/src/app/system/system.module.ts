import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemRoutingModule, routedComponents } from './system-routing.module';
import { SharedModule } from '../shared';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { SystemPublicModule } from './public/public.module';
// import { SystemRoutingModule, routedComponents } from "./system-routing.module";
// import { SharedModule } from 'app/shared';

@NgModule({
    imports: [
        SharedModule,
        CommonModule,
        SystemRoutingModule,
        SystemPublicModule
    ],
    declarations: [...routedComponents]
})
export class SystemModule { }
