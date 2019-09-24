import {NgModule} from '@angular/core';
import {ServerModule} from '@angular/platform-server';
import {ModuleMapLoaderModule} from '@nguniversal/module-map-ngfactory-loader';

import {AppModule} from './app.module';
import {AppComponent} from './app.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { UniversalInterceptor } from './core/interceptor/universal-interceptor';

@NgModule({
  imports: [
    AppModule,
    ServerModule,
    ModuleMapLoaderModule // 非常重要，用来支持惰性加载的
  ],
  bootstrap: [AppComponent],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: UniversalInterceptor,
    multi: true
  }],
})
export class AppServerModule {}
