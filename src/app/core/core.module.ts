import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutModule } from './layout/layout.module';
import { QyUeditorMdModule } from './public/editor-md/editor-md.module';

const QY_MODULES = [
  QyUeditorMdModule
];


@NgModule({
  imports: [
    CommonModule,
    LayoutModule,
    ...QY_MODULES
  ],
  declarations: [],
  exports: [
    ...QY_MODULES
  ],
  providers: [
  ]
})
export class CoreModule { }

export * from './public/editor-md';
