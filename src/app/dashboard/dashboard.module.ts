import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule, routedComponents } from './dashboard-routing.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    DashboardRoutingModule,
  ],
  declarations: [...routedComponents]
})
export class DashboardModule { }
