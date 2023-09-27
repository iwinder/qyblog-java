import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routedComponents, HomeRoutingModule } from './home-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
    imports: [
        CommonModule,
        HomeRoutingModule,
        NgbModule
    ],
    declarations: [...routedComponents]
})
export class HomeModule { }
