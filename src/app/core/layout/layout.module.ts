import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { HeaderComponent } from './header/header.component';
import { RouterModule } from '@angular/router';


import { ContentComponent } from './content/content.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeadertComponent } from './header/header.component';


@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        NgbModule
    ],
    declarations: [ContentComponent, HeadertComponent],
    exports: [ContentComponent],
    providers: []
})
export class LayoutModule { }
