import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DashboardRoutingModule, routedComponents } from './dashboard-routing.module';
import { WorkspaceComponent } from './workspace/workspace.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    DashboardRoutingModule,
  ],
  // declarations: [ WorkspaceComponent],
  declarations: [...routedComponents]
})
export class DashboardModule { }
