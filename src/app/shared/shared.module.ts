import { NgZorroAntdModule, NZ_I18N, zh_CN  } from 'ng-zorro-antd';
import { NgModule, ModuleWithProviders } from '@angular/core';
import {  CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgZorroAntdModule.forRoot(),
    ReactiveFormsModule,


  ],
  declarations: [

  ],
  exports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    NgZorroAntdModule,
    ReactiveFormsModule,
  ]
})
export class SharedModule {

  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [{ provide: NZ_I18N, useValue: zh_CN }]
    };
  }

}
