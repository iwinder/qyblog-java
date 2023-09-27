import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { RouterModule } from '@angular/router';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { NavLeftComponent } from './nav-left/nav-left.component';
import { ContentComponent } from './content/content.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
    imports: [
        CommonModule,
        NgZorroAntdModule,
        RouterModule
    ],
    declarations: [ContentComponent, HeaderComponent, FooterComponent, NavLeftComponent],
    exports: [ContentComponent],
    providers: []
})
export class LayoutModule { }
