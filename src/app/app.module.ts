import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from 'app/shared';
import { AppRoutingModule } from './app-routing.module';
import { ContentComponent } from './core/layout/content/content.component';


@NgModule({
  declarations: [
    AppComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    // HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule.forRoot()
  ],
  exports: [ContentComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
