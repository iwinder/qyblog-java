import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { QyLoginComponent } from './login/login.component';

import { RouterModule } from '@angular/router';
import { AuthRoutingModule } from './auth-routing.module';
import { AuthService } from './auth.service';
import { AuthGuard } from './auth-guard.service';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { httpInterceptorProviders } from './http-Interceptor-providers';

@NgModule({
  imports: [
    RouterModule,
    NgZorroAntdModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AuthRoutingModule
  ],
  declarations: [QyLoginComponent],
  exports: [],
  providers: [AuthService, AuthGuard, httpInterceptorProviders]
})
export class AuthModule { }
