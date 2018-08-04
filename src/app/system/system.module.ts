import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemRoutingModule, routedComponents } from './system-routing.module';
import { SharedModule } from '../shared';
// import { SystemRoutingModule, routedComponents } from "./system-routing.module";
// import { SharedModule } from 'app/shared';

@NgModule({
    imports: [
        SharedModule,
        // SystemModule,
        CommonModule,
        SystemRoutingModule,
        // UgcRoutingModule,
        // UgcPublicModule
    ],
    declarations: [...routedComponents]
})
export class SystemModule { }
