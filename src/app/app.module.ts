import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { AppComponent } from './app.component';
import { LayoutModule } from './core/layout/layout.module';
import { AppRoutingModule, routedComponents } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { MarkdownModule } from 'ngx-markdown';
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
  ],
  providers: [
    CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
