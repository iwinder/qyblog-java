import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QyLoginComponent } from './login/login.component';


const routes: Routes = [
  { path: 'login', component: QyLoginComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule { }

export const routedComponents = [QyLoginComponent];
