import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { MAIN_MENU } from './menu-data';

@Component({
    selector: 'qy-contnet',
    templateUrl: './content.component.html',
    styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit {
    isCollapsed = false;
    triggerTemplate = null;
    navItems = MAIN_MENU;
    @ViewChild('trigger') customTrigger: TemplateRef<void>;

    constructor() {
    }

    ngOnInit() {
        console.log('ContentComponent:', 22222);
    }

        /** custom trigger can be TemplateRef **/
        changeTrigger(): void {
            this.triggerTemplate = this.customTrigger;
          }

    choseIsCollapsed(isCollapsed) {
        this.isCollapsed = isCollapsed;
    }
}
