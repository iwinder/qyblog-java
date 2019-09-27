import { BrowserModule, BrowserTransferStateModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { AppComponent } from './app.component';
import { LayoutModule } from './core/layout/layout.module';
import { AppRoutingModule, routedComponents } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { MarkdownModule } from 'ngx-markdown';
import { TransferHttpCacheModule } from '@nguniversal/common';

@NgModule({
  declarations: [
    ...routedComponents,
    AppComponent
  ],
  imports: [
    // 加上下面这句，appId就是上面提到用于替换的唯一标识
    BrowserModule.withServerTransition({appId: 'qy-web'}),
    BrowserModule,
    HttpClientModule,
    LayoutModule,
    AppRoutingModule,
    NgbModule,
    MarkdownModule.forRoot(),
    BrowserTransferStateModule, // 在客户端导入，用于实现将状态从服务器传输到客户端
    TransferHttpCacheModule, // 用于实现服务器到客户端的请求传输缓存，防止客户端重复请求服务端已完成的请求
  ],
  providers: [
    CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
