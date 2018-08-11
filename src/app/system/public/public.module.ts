import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'app/shared';
import { QyUserFormComponent } from './user-form/user-form.component';


const PUBLIC_COMPS = [
    QyUserFormComponent
];

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [...PUBLIC_COMPS],
  exports: [...PUBLIC_COMPS],
  entryComponents: [],
  providers: []
})
export class SystemPublicModule { }
