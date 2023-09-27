import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditorMdComponent } from './editor-md.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [EditorMdComponent],
  exports: [EditorMdComponent]
})
export class QyUeditorMdModule { }
